package com.fpt.capstone.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Services.AccountService;
import com.fpt.capstone.Services.TransactionService;
import com.fpt.capstone.Utils.TransactionStatus;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;
    
    @Autowired 
    private ServletContext context;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewAccount() {
        ModelAndView m = new ModelAndView("transaction");
        return m;
    }

    /**
     * Tạo transaction thu phí thủ công khi staff enter biển số xe
     *
     * @param licensePlate biển số xe do staff nhập vào
     * @param laneId mã làn của staff thực hiện thu phí
     * @return trả về thông tin transaction vừa được tạo
     */
    @RequestMapping(value = "makeManualPayment/{licensePlate}/{laneId}")
    @ResponseStatus(HttpStatus.OK)
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
    public TransactionDTO updateTransaction(@PathVariable String id) {
        return transactionService.updateTransactionStatus(id, TransactionStatus.TRANS_SUCCESS);
    }

    /**
     * Cập nhật trạng thái thu phí thủ công/tự động khi staff đã mở cổng cho xe
     * qua
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "finish/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO finishTransaction(@PathVariable String id) {
        return transactionService.updateTransactionStatus(id, TransactionStatus.TRANS_FINISH);
    }

    /**
     * Kiểm tra biển số xe mà camera gửi về, nếu hợp lệ thì tạo một transaction
     * mới với trạng thái là Initial
     *
     * @param stationId
     * @param plate
     * @param img
     * @param context
     * @return
     */
    @RequestMapping(value = "camera{stationId}/detect/{plate}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String createTransactionByCamera(@PathVariable int stationId, @PathVariable String plate,
            @RequestParam(name = "img") MultipartFile img) {

        
        String result = "false";      
        System.out.println("Plate: " + plate);
        
        Date now = new Date();                                
        String fileName = plate + "_" + now.getTime() + ".jpg";
        String filePath = context.getRealPath(".") + "/WEB-INF/images/plates/" + fileName;
                      
        try {            
            byte[] imgBytes = img.getBytes();
                                    
            
            File fileImg = new File(filePath);
            if (!fileImg.exists()) {
                fileImg.createNewFile();
            }                                    
            
            FileOutputStream fos = new FileOutputStream(fileImg);
            fos.write(imgBytes);
            fos.flush();
            fos.close();
            
            try {
                transactionService.createCapturedTransaction(plate, fileName, now, stationId);
                System.out.println("Created transction for plate: " + plate);
            } catch (Exception e) {
                String filePathDump = context.getRealPath(".") + "/WEB-INF/images/dumps/" + fileName;
                File fileImgDump = new File(filePathDump);

                System.out.println("What exception? " + e.getMessage());
                if (e.getMessage().equals("This license plate does not exist")) {
                    Files.move(fileImg.toPath(), fileImgDump.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Plate " + plate + " not found, move to dump folder");
                }
            }

            result = "true";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Khi tài xế yêu cầu payment, và transaction chưa dc tạo bởi camera, nếu
     * không thì tạo transaction thu phí tự động khi tài xế xác nhận đồng ý
     * thanh toán
     *
     * @param username tài khoản của tài xế
     * @param stationId mã trạm do android app gửi lên
     * @return trả về thông tin transaction vừa dc tạo
     */
    @RequestMapping(value = "makePayment/{username}/{stationId}/{isCreated}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO insertTransactionPayment(@PathVariable String username, @PathVariable int stationId) {

        System.out.println("Request Make payment from Driver in station " + stationId);

        // Khởi tạo transaction, status: Chưa thanh toán
        TransactionDTO transDTO = transactionService.insertAutoTransaction(username, stationId);

        System.out.println("   + create transaction [" + transDTO.getId() + "] success with status ["
                + transDTO.getStatus() + "]");

        // Gọi module paypal
        String result = accountService.makePayment(username, stationId);
        if (result.equals("")) {
            transDTO = transactionService.updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_SUCCESS);
        } else {
            transDTO = transactionService.updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_FAILED);
            transDTO.setFailReason(result);
        }

        // status:
        System.out.println("   + update transaction success with status [" + transDTO.getStatus() + "]");
        return transDTO;

    }

    /**
     * Khi tài xế yêu cầu payment, và transaction đã dc tạo bởi camera, chỉ cần update lại status
     *
     * @param transactionId id transaction đã được tạo    
     * @return trả về thông tin transaction vừa dc cập nhật
     */
    @RequestMapping(value = "makePayment/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO insertTransactionPayment(@PathVariable String transactionId) {

        System.out.println("Request payment from Driver for transaction " + transactionId);

        // Khởi tạo transaction, status: Chưa thanh toán
        TransactionDetailDTO transDTO = transactionService.getDetailById(transactionId);

        System.out.println("   + get transaction [" + transDTO.getId() + "] with status ["
                + transDTO.getStatus() + "]");

        // Gọi module paypal
        String result = accountService.makePayment(transDTO.getUsername(), transDTO.getStationId());
        TransactionDTO transResultDTO;
        if (result.equals("")) {
            transResultDTO = transactionService.updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_SUCCESS);
        } else {
            transResultDTO = transactionService.updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_FAILED);
            transResultDTO.setFailReason(result);
        }

        // status:
        System.out.println("   + update transaction success with status [" + transResultDTO.getStatus() + "]");
        return transResultDTO;

    }

    
    /**
     * Lấy transaction detail theo vehicle id trong vòng 24h
     *
     * @param vehicleId
     * @return
     */
    @RequestMapping(value = "/getDetailByVehicleIdIn24Hours/{vehicleId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDetailByVehicleIdIn24Hours(@PathVariable String vehicleId) {
        System.out.println("GET DETAILS BY VEHICLE ID IN 24 HOURS");
        List<TransactionDetailDTO> dtos
                = transactionService.getDetailByVehicleIdIn24Hours(Integer.parseInt(vehicleId));

        Map<String, Object> map = new HashMap<>();
        map.put("TransactionDetails", dtos);

        return map;
    }

//    /**
//     * Staff gửi yêu cầu lấy danh sách các xe đang vào lane mình trực Server gửi
//     * danh sách các xe này và cập nhật trạng thái thành Đang xử lý
//     *
//     * @param laneId
//     * @return
//     */
//    @RequestMapping(value = "getResult")
//    @ResponseStatus(HttpStatus.OK)
//    public List<TransactionDTO> getListResultTransactionByLane() {
//        List<TransactionDTO> result = transactionService.getTransactionsForStaff("Chưa thanh toán");
//
//        for (TransactionDTO tran : result) {
//            if (!tran.getStatus().display().endsWith("Đang xử lý")) {
//                String status = tran.getStatus().display().replace("Chờ xử lý", "Đang xử lý");
//                transactionService.updateTransactionStatus(tran.getId(), status);
//            }
//        }
//        return result;
//    }

    /**
     * Lấy transaction detail theo id
     *
     * @param transactionId
     * @return
     */
    @RequestMapping(value = "getDetail/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDetailDTO getById(@PathVariable String transactionId) {
        TransactionDetailDTO dto = transactionService.getDetailById(transactionId);

        return dto;
    }

    /**
     * Lấy list transaction detail
     *
     * @return
     */
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    public String getAllTransDetail() throws JsonProcessingException {

        List<TransactionDetailDTO> dtos = transactionService.getAllDetail();

        return new Gson().toJson(dtos);
    }

    /**
     * Khi xe qua trạm nhưng thanh toán thất bại, tài xế yêu cầu thanh toán lại
     * thì thực hiện
     *
     * @param transactionId
     * @return
     */
    @RequestMapping(value = "updateProcessingTransaction/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> requestPaymentLater(@PathVariable String transactionId) {
        Map<String, String> map = new HashMap<>();

        String status = "";

        TransactionDetailDTO transDTO = transactionService.getDetailById(transactionId);

        System.out.println(
                "   + get transaction [" + transDTO.getId() + "] success with status [" + transDTO.getStatus() + "]");

        // Gọi module paypal
        String result = accountService.makePayment(transDTO.getUsername(), transDTO.getStationId());
        if (result.equals("")) {
            transactionService.updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_SUCCESS);
            status = "Kết Thúc";
        } else {
            transactionService.updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_FAILED);
            status = "Thất bại";
        }

        // status:
        System.out.println("   + update transaction success with status: " + status);

        map.put("status", status);
        map.put("reason", result);

        return map;
    }

}
