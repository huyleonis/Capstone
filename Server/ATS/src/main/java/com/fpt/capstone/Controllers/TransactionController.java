package com.fpt.capstone.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Dtos.VehicleDTO;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Services.AccountService;
import com.fpt.capstone.Services.TransactionService;
import com.fpt.capstone.Services.VehicleService;
import com.fpt.capstone.Utils.TransactionStatus;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ServletContext context;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewTransaction() {
        ModelAndView m = new ModelAndView("transaction");
        return m;
    }

    /**
     * Tạo transaction thu phí thủ công khi staff enter biển số xe
     *
     * @param licensePlate biển số xe do staff nhập vào
     * @param laneId       mã làn của staff thực hiện thu phí
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
     * @param username  tài khoản của tài xế
     * @param stationId mã trạm do android app gửi lên
     * @return trả về thông tin transaction vừa dc tạo
     */
    @RequestMapping(value = "makePayment/{username}/{stationId}")
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

        System.out.println("Request payment from Driver for captured transaction " + transactionId);

        // Lấy transaction đã dc khởi tạo bởi camera 
        TransactionDetailDTO detailDTO = transactionService.getDetailById(transactionId);
        String username = detailDTO.getUsername();

        // update sang trạng thái pending, chờ xử lý giao dịch
        TransactionDTO transDTO = transactionService.updateTransactionStatus(transactionId, TransactionStatus.TRANS_PENDING);

        System.out.println("   + get transaction [" + transDTO.getId() + "] with status ["
                + transDTO.getStatus() + "]");


        // Xử lý thanh toán
        String result = accountService.makePayment(username, transDTO.getStationId());
        TransactionDTO transResultDTO;
        if (result.equals("")) {
            transResultDTO = transactionService
                    .updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_SUCCESS);
        } else {
            transResultDTO = transactionService
                    .updateTransactionStatus(transDTO.getId(), TransactionStatus.TRANS_FAILED);
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

    @RequestMapping(value = "/reportTransaction/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> reportTransaction(@PathVariable String transactionId) {
        Map<String, String> map = new HashMap<>();

        String result = "";
        TransactionStatus status = TransactionStatus.TRANS_ERROR;

        TransactionDTO dto =
                transactionService.updateTransactionStatus(transactionId, status);
        if (dto != null) {
            result = "success";
        } else {
            result = "fail";
        }

        System.out.println("Update transaction success with status: " + status);

        map.put("result", result);
        map.put("status", status.getName());

        return map;
    }

    /**
     * Lấy list report
     *
     * @return
     */
    @RequestMapping(value = "/getReportDetail", method = RequestMethod.GET)
    public String getAllReportTransaction() throws JsonProcessingException {

        List<TransactionDetailDTO> dtos = transactionService.getAllReportDetail();

        return new Gson().toJson(dtos);
    }

    @RequestMapping(value = "/updateReport", method = RequestMethod.POST)
    public String updateReport(@RequestBody Transaction transaction) {
        boolean isSuccessful = false;

        TransactionDTO transactionDTO = transactionService.updateReport(transaction);

        if (transactionDTO != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/deleteReport", method = RequestMethod.POST)
    public String deleteReport(@RequestBody Transaction transaction) {
        boolean isSuccessful = false;

        isSuccessful = transactionService.delete(transaction.getId());

        return (isSuccessful) ? "success" : "fail";
    }
    
    @RequestMapping(value = "/getTransByLicPlateAndTime", method = RequestMethod.GET)
    public String getTransByVehicleIdAndTime(@RequestParam(name = "vehicleId") int vehicleId, 
    			@RequestParam(name = "createdTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createdTime) {
		
    	List<TransactionDTO> dtos = transactionService
    			.getTransByVehicleIdAndTime(vehicleId, createdTime);
    	
    	return new Gson().toJson(dtos);
	}
    
    @RequestMapping(value = "/getDumpToPhoto")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getDumpToPhoto(){

        String basePath = context.getRealPath(".");
        File folder = new File (basePath + "/WEB-INF/images/dumps");
        File[] listOfFile = folder.listFiles();
        List<String> listFile = new ArrayList<>();
        if(folder.isDirectory()){
            for(int i = 0; i < listOfFile.length; i++){
                if(listOfFile[i].isFile()){
                    System.out.println("File: " + listOfFile[i].getName());
                    
                    String str = listOfFile[i].getName();
                    
                    StringTokenizer st = new StringTokenizer(str, "_.");
                    
                    while(st.hasMoreElements()) {
                    	System.out.println(st.nextElement());
                    }
                    
                    listFile.add(listOfFile[i].getName());
                }
            }
        }
        return listFile;
    }
}
