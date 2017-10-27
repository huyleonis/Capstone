package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.LaneDTO;
import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Dtos.ResponseDTO;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LaneService laneService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private AccountService accountService;

    

    /**
     * Tạo transaction thu phí thủ công khi staff enter biển số xe
     *
     * @param licensePlate biển số xe do staff nhập vào
     * @param laneId       mã làn của staff thực hiện thu phí
     * @return trả về thông tin transaction vừa được tạo
     */
    @RequestMapping(value = "makeManualPayment/{licensePlate}/{laneId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO insertTransactionPaymentManual(@PathVariable String licensePlate, @PathVariable int laneId) {
        return transactionService.insertManualTransaction(licensePlate, laneId);
    }

    /**
     * Cập nhật trạng thái thu phí thủ công khi staff đã nhận tiền
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "receivedMoney/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO updateTransaction(@PathVariable String id) {
        return transactionService.updateTransactionStatus(id, "Đang xử lý");
    }

    /**
     * Cập nhật trạng thái thu phí thủ công/tự động khi staff đã mở cổng cho xe qua
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "finish/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO finishTransaction(@PathVariable String id) {
        return transactionService.updateTransactionStatus(id, "Hoàn thành giao dịch");
    }


    /**
     * Tạo transaction thu phí tự động khi tài xế xác nhận đồng ý thanh toán
     *
     * @param username  tài khoản của tài xế
     * @param stationId mã trạm do android app gửi lên
     * @return trả về thông tin transaction vừa dc tạo
     */
    @RequestMapping(value = "makePayment/{username}/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO insertTransactionPayment(@PathVariable String username, @PathVariable int stationId) {
        System.out.println("Request Make payment from Driver in station " + stationId);

        // Khởi tạo transaction, status: Chưa thanh toán
        TransactionDTO transDTO = transactionService.insertAutoTransaction(username, stationId);

        System.out.println("   + create transaction [" + transDTO.getTransactionId() + "] success with status [" + transDTO.getStatus() + "]");

        // Gọi module paypal
        //....
        transDTO = transactionService.updateTransactionStatus(transDTO.getTransactionId(), "Thành công");
        // status:

        System.out.println("   + update transaction success with status [" + transDTO.getStatus() + "]");
        return transDTO;
    }

    /**
     * Khi xe nhận tín hiệu beacon 2, gửi yêu cầu check result. Server cập nhật trạng
     *
     * @param uuid
     * @param idTransaction
     * @return
     */
    @RequestMapping(value = "checkResult/{uuid}/{idTransaction}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO checkResult(@PathVariable String uuid, @PathVariable String idTransaction) {

        System.out.println("Request Check result from Driver");

        TransactionDTO transDTO = transactionService.getById(idTransaction);
        if (transDTO.getStatus().endsWith("Chờ xử lý")) {
            return transDTO;
        }

        String status = transDTO.getStatus() + " - Chờ xử lý";

        //Cập nhật làn xe vô
        LaneDTO laneDTO = laneService.getLaneByBeacon(uuid);
        transDTO = transactionService.updateTransactionLane(idTransaction, laneDTO.getId());

        System.out.println("   + Update trans (1) lane [" + transDTO.getLaneId() + "] and status [" + transDTO.getStatus() + "]");

        //Cập nhật trạng thái thành chờ xử lý
        transDTO = transactionService.updateTransactionStatus(idTransaction, status);

        System.out.println("   + Update trans (2) lane [" + transDTO.getLaneId() + "] and status [" + transDTO.getStatus() + "]");
        return transDTO;
    }

    /**
     * Staff gửi yêu cầu lấy danh sách các xe đang vào lane mình trực
     * Server gửi danh sách các xe này và cập nhật trạng thái thành Đang xử lý
     *
     * @param laneId
     * @return
     */
    @RequestMapping(value = "getResult")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDTO> getListResultTransactionByLane() {
        List<TransactionDTO> result = transactionService.getTransactionsForStaff("Chưa thanh toán");

        for (TransactionDTO tran : result) {
            if (!tran.getStatus().endsWith("Đang xử lý")) {
                String status = tran.getStatus().replace("Chờ xử lý", "Đang xử lý");
                transactionService.updateTransactionStatus(tran.getTransactionId(), status);
            }
        }
        return result;
    }


    @RequestMapping(value = "/updateProcessingTransaction/{transactionId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> updateProcessingTransaction(@PathVariable String transactionId) {
        Map<String, String> map = new HashMap<>();
        String status = "";

        TransactionDTO transactionDTO = transactionService.getById(transactionId);
        PriceDTO priceDTO = priceService.findPriceById(transactionDTO.getPriceId());
        int isSuccessful = accountService.updateAccountBalance(priceDTO.getPrice(), transactionDTO.getUsernameId());

        if (isSuccessful > 0) {
            status = "Thành Công";
            transactionService.updateTransactionStatus(transactionDTO.getTransactionId(), status);
        } else {
            status = "Sô dư không đủ chi trả";
        }

        map.put("status", status);

        return map;
    }
}
