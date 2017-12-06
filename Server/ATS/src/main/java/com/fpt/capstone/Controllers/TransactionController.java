package com.fpt.capstone.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.PhotoDTO;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Services.AccountService;
import com.fpt.capstone.Services.TransactionService;
import com.fpt.capstone.Services.VehicleService;
import com.fpt.capstone.Utils.TransactionStatus;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    static final int ACTIVE = 8;

    /**
     * Hiển thị trang transaction.jsp
     *
     * @return Transaction view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewTransaction() {
        ModelAndView m = new ModelAndView("transaction");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Transaction Management");
        return m;
    }

    /**
     * Tạo transaction thu phí thủ công khi staff enter biển số xe
     *
     * @param licensePlate biển số xe
     * @param laneId mã làn của staff thực hiện thu phí
     * @return trả về thông tin transaction vừa được tạo
     */
    @RequestMapping(value = "makeManualPayment/{licensePlate}/{laneId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO insertTransactionPaymentManual(@PathVariable String licensePlate, @PathVariable int laneId) {
        System.out.println("Make transaction in Desktop with " + licensePlate);
        return transactionService.insertManualTransaction(licensePlate, laneId);
    }

    /**
     * Cập nhật trạng thái thu phí thành SUCCESS
     *
     * @param id
     * @return trả về thông tin transaction
     */
    @RequestMapping(value = "receivedMoney/{id}")
    public TransactionDTO updateTransaction(@PathVariable String id) {
        System.out.println("Success transaction " + id);
        return transactionService.updateTransactionStatus(id, TransactionStatus.TRANS_SUCCESS);
    }

    /**
     * Cập nhật trạng thái thu phí thành FINISH
     *
     * @param id
     * @return trả về thông tin transaction
     */
    @RequestMapping(value = "finish/{id}")
    public TransactionDTO finishTransaction(@PathVariable String id) {
        System.out.println("Finish transaction" + id);
        return transactionService.updateTransactionStatus(id, TransactionStatus.TRANS_FINISH);
    }

    /**
     * Kiểm tra biển số xe mà camera gửi về, nếu hợp lệ thì tạo một transaction
     * mới với trạng thái là Initial
     *
     * @param stationId mã trạm
     * @param plate biển số xe
     * @param img tên hình ảnh chụp được
     * @return Kết quả thực hiện
     */
    @RequestMapping(value = "camera{stationId}/detect/{plate}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createTransactionByCamera(@PathVariable int stationId, @PathVariable String plate,
            @RequestParam(name = "img") MultipartFile img) {

        String result = "false";
        System.out.println("Plate: " + plate);
        Date now = new Date();
        String fileName = plate + "_" + now.getTime() + ".jpg";

        // Đường dẫn đến thư mục lấy file ảnh:
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
    @RequestMapping(value = "makePayment/{username}/{stationId}")
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
     * Khi tài xế yêu cầu payment, và transaction đã dc tạo bởi camera, chỉ cần
     * update lại status
     *
     * @param transactionId id transaction đã được tạo
     * @return trả về thông tin transaction vừa dc cập nhật
     */
    @RequestMapping(value = "makePayment/{transactionId}")
    public TransactionDTO insertTransactionPayment(@PathVariable String transactionId) {

        System.out.println("Request payment from Driver for captured transaction " + transactionId);

        // Lấy transaction đã dc khởi tạo bởi camera
        TransactionDetailDTO detailDTO = transactionService.getDetailById(transactionId);
        String username = detailDTO.getUsername();

        // update sang trạng thái pending, chờ xử lý giao dịch
        TransactionDTO transDTO = transactionService.updateTransactionStatus(transactionId,
                TransactionStatus.TRANS_PENDING);

        System.out
                .println("   + get transaction [" + transDTO.getId() + "] with status [" + transDTO.getStatus() + "]");

        // Xử lý thanh toán
        String result = accountService.makePayment(username, transDTO.getStationId());
        TransactionDTO transResultDTO;
        if (result.equals("")) {
            transResultDTO = transactionService.updateTransactionStatus(transDTO.getId(),
                    TransactionStatus.TRANS_SUCCESS);
        } else {
            transResultDTO = transactionService.updateTransactionStatus(transDTO.getId(),
                    TransactionStatus.TRANS_FAILED);
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
     * @return List transaction
     */
    @RequestMapping(value = "/getDetailByVehicleIdIn24Hours/{vehicleId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDetailByVehicleIdIn24Hours(@PathVariable String vehicleId) {
        System.out.println("GET DETAILS BY VEHICLE ID IN 24 HOURS");
        List<TransactionDetailDTO> dtos = transactionService.getDetailByVehicleIdIn24Hours(Integer.parseInt(vehicleId));

        Map<String, Object> map = new HashMap<>();
        map.put("TransactionDetails", dtos);

        return map;
    }

    /**
     * Lấy transaction detail theo id
     *
     * @param transactionId
     * @return transaction tìm được
     */
    @RequestMapping(value = "getDetail/{transactionId}")
    public TransactionDetailDTO getById(@PathVariable String transactionId) {
        return transactionService.getDetailById(transactionId);
    }

    /**
     * Lấy list transaction detail
     *
     * @return danh sách transaction dưới dạng JSONARRAY
     * @throws com.fasterxml.jackson.core.JsonProcessingException
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
     * @return status và result
     */
    @RequestMapping(value = "payLater/{transactionId}")
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

    /**
     * Báo cáo Transaction theo id Transaction bị báo cáo sẽ update status thành
     * ERROR
     *
     * @param transactionId
     * @return Kết quả thực hiện và tên Status được update
     */
    @RequestMapping(value = "/reportTransaction/{transactionId}")
    public Map<String, String> reportTransaction(@PathVariable String transactionId) {
        Map<String, String> map = new HashMap<>();

        String result = "";
        TransactionStatus status = TransactionStatus.TRANS_ERROR;

        TransactionDTO dto = transactionService.updateTransactionStatus(transactionId, status);
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
     * Tìm danh sách Transaction dựa vào biển số xe và thời gian khởi tạo
     * Transaction (+/- 30p)
     *
     * @param licensePlate
     * @param createdTime
     * @return danh sách transaction theo biển số xe
     */
    @RequestMapping(value = "/getTransByLicPlateAndTime", method = RequestMethod.GET)
    public String getTransByLicPlateAndTime(@RequestParam(name = "licensePlate") String licensePlate,
            @RequestParam(name = "createdTime") String createdTime) {

        Date date = new Date(Long.parseLong(createdTime));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String processedDate = df.format(date);
        List<TransactionDTO> dtos = transactionService
                .getTransByLicPlateAndTime(licensePlate, processedDate);
        return new Gson().toJson(dtos);
    }

    /**
     * Lấy transaction detail theo id cho desktop Kiểm tra transaction được tạo
     * trong 30p với status "Initial" và "Not pay"
     *
     * @param licensePlate
     * @param stationId
     * @return transaction
     */
    @RequestMapping(value = "getCapturedTransaction/{licensePlate}/{stationId}")
    public TransactionDetailDTO getCapturedTransactionForDesktop(@PathVariable String licensePlate, @PathVariable int stationId) {
        int vehicleId = vehicleService.getVehicleId(licensePlate);
        return transactionService.getCapturedTransactionForDesktop(vehicleId, stationId);
    }

    /**
     * Lấy transaction chưa thanh toán theo biển số xe
     *
     * @param licensePlate
     * @return transaction
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "findTransactionNotPay/{licensePlate}")
    public String findTransactionNotPay(@PathVariable String licensePlate) throws JsonProcessingException {
        List<TransactionDTO> dtos = transactionService.findVehicleNotPay(licensePlate);
        return new Gson().toJson(dtos);
    }

    /**
     * Xác nhận report đúng.
     *
     * @param transId
     * @param licensePlate
     * @return transaction
     *
     */
    @RequestMapping(value = "/confirmReport")
    public String confirmReport(@RequestParam(name = "transId") String transId,
            @RequestParam(name = "licensePlate") String licensePlate) {

        boolean isSuccessful = false;

        isSuccessful = transactionService.confirmReport(transId, licensePlate);

        if (isSuccessful) {
            return "success";
        }

        return "fail";
    }

    @RequestMapping(value = "/dismissReport")
    public boolean dismissReport(@RequestParam(name = "transId") String transId) {
        TransactionDTO dto = transactionService.updateTransactionStatus(transId, TransactionStatus.TRANS_NOTPAY);
        if(dto == null)
            return false;
        return true;

    }
    @RequestMapping(value = "/getDumpToPhoto")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDTO> getDumpToPhoto() {

        String basePath = context.getRealPath(".");
        File folder = new File(basePath + "/WEB-INF/images/dumps");
        File[] listOfFile = folder.listFiles();
        List<PhotoDTO> listFile = new ArrayList<>();
        if (folder.isDirectory()) {
            for (int i = 0; i < listOfFile.length; i++) {
                if (listOfFile[i].isFile()) {

                    String str = listOfFile[i].getName();

                    StringTokenizer st = new StringTokenizer(str, "_.");
                    String licensePlate = st.nextToken();
                    String createdTime = st.nextToken();

                    Date date = new Date(Long.parseLong(createdTime));
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    PhotoDTO dto = new PhotoDTO(str, licensePlate, df.format(date));

                    listFile.add(dto);
                }
            }
        }

        return listFile;
    }

}
