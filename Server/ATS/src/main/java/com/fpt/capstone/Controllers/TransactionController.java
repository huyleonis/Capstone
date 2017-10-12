package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.LaneDTO;
import com.fpt.capstone.Dtos.ResponseDTO;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Services.AccountServiceImpl;
import com.fpt.capstone.Services.LaneService;
import com.fpt.capstone.Services.LaneServiceImpl;
import com.fpt.capstone.Services.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private LaneServiceImpl laneServiceImpl;
    
    @Autowired
    private AccountServiceImpl accountServiceImpl;

//    @RequestMapping(value = "findByLicensePlate/{license_plate}/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public TransactionDTO findByLicensePlate(@PathVariable String license_plate, @PathVariable int id){
//        return transactionServiceImpl.findByLicensePlate(license_plate, id);
//    }

    /**
     * Tạo transaction thu phí thủ công khi staff enter biển số xe
     * @param licensePlate biển số xe do staff nhập vào
     * @param  laneId mã làn của staff thực hiện thu phí
     * @return trả về thông tin transaction vừa được tạo
     */
    @RequestMapping(value = "makeManualPayment/{licensePlate}/{laneId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO insertTransactionPaymentManual(@PathVariable String licensePlate, @PathVariable int laneId ){
        return  transactionServiceImpl.insertManualTransaction(licensePlate, laneId);
    }

    /**
     * Cập nhật trạng thái thu phí thủ công khi staff đã nhận tiền
     * @param id
     * @return
     */
    @RequestMapping(value = "receivedMoney/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO updateTransaction(@PathVariable String id){
        return transactionServiceImpl.updateTransactionStatus(id, "Đang xử lý");
    }

    /**
     * Cập nhật trạng thái thu phí thủ công/tự động khi staff đã mở cổng cho xe qua
     * @param id
     * @return
     */
    @RequestMapping(value = "finish/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO finishTransaction(@PathVariable String id){
        return transactionServiceImpl.updateTransactionStatus(id, "Kết thúc");
    }




    /**
     * Tạo transaction thu phí tự động khi tài xế xác nhận đồng ý thanh toán
     * @param username tài khoản của tài xế
     * @param stationId mã trạm do android app gửi lên
     * @return trả về thông tin transaction vừa dc tạo
     */
    @RequestMapping(value = "makePayment/{username}/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO insertTransactionPayment(@PathVariable String username, @PathVariable int stationId ){
        System.out.println("Request Make payment from Driver in station " +  stationId);
        
        // Khởi tạo transaction, status: Chưa thanh toán
        TransactionDTO transDTO =  transactionServiceImpl.insertAutoTransaction(username, stationId);
        
        System.out.println("   + create transaction ["+transDTO.getId()+"] success with status [" + transDTO.getStatus() + "]");

        // Gọi module paypal
        String result = accountServiceImpl.makePayment(username, stationId);
        if (result.equals("")) {
            transDTO = transactionServiceImpl.updateTransactionStatus(transDTO.getId(), "Thành công");
        } else {
            transDTO = transactionServiceImpl.updateTransactionStatus(transDTO.getId(), "Thất bại");
            transDTO.setFailReason(result);
        }
        
        // status:

        System.out.println("   + update transaction success with status [" + transDTO.getStatus() + "]");
        return transDTO;
    }

    /**
     * Khi xe nhận tín hiệu beacon 2, gửi yêu cầu check result. Server cập nhật trạng
     * @param laneId
     * @param idTransaction
     * @return
     */
    @RequestMapping(value = "checkResult/{laneId}/{idTransaction}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO checkResult(@PathVariable String laneId, @PathVariable String idTransaction) {

        System.out.println("Request Check result from Driver in lane with ID " + laneId);
        
        int iLaneId = Integer.parseInt(laneId);
        
        TransactionDTO transDTO =  transactionServiceImpl.getById(idTransaction);
        if (transDTO.getStatus().endsWith("Chờ xử lý")) {
            return transDTO;
        }

        String status =  transDTO.getStatus() + " - Chờ xử lý";

        //Cập nhật làn xe vô
        transDTO = transactionServiceImpl.updateTransactionLane(idTransaction, iLaneId);
        
        System.out.println("   + Update trans (1) lane ["+ transDTO.getLaneId() +"] and status ["+transDTO.getStatus()+"]");

        //Cập nhật trạng thái thành chờ xử lý
        transDTO = transactionServiceImpl.updateTransactionStatus(idTransaction, status);

        System.out.println("   + Update trans (2) lane ["+ transDTO.getLaneId() +"] and status ["+transDTO.getStatus()+"]");
        return transDTO;
    }

    /**
     * Staff gửi yêu cầu lấy danh sách các xe đang vào lane mình trực
     * Server gửi danh sách các xe này và cập nhật trạng thái thành Đang xử lý
     * @param laneId
     * @return
     */
    @RequestMapping(value = "getResult/{laneId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDTO> getListResultTransactionByLane(@PathVariable int laneId) {
        List<TransactionDTO> result = transactionServiceImpl.getTransactionsForStaff(laneId, "Chờ xử lý");

        for (TransactionDTO tran: result) {
            if (!tran.getStatus().endsWith("Đang xử lý")) {
                String status = tran.getStatus().replace("Chờ xử lý", "Đang xử lý");
                transactionServiceImpl.updateTransactionStatus(tran.getId(), status);
            }
        }
        return result;
    }


}
