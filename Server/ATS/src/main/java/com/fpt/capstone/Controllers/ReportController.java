package com.fpt.capstone.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import static com.fpt.capstone.Controllers.TransactionController.ACTIVE;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Services.TransactionService;
import com.google.gson.Gson;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private TransactionService transactionService;

    static final int ACTIVE = 9;

    /**
     * Hiển thị trang report.jsp
     *
     * @return Report view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewReport() {
        ModelAndView m = new ModelAndView("report");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Report Management");
        return m;
    }

    @RequestMapping(value = "/report_{transactionId}_{key}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewReport(@PathVariable String transactionId, @PathVariable String key) {
        ModelAndView m = new ModelAndView("report");
        m.addObject("transactionId", transactionId);
        m.addObject("key", key);
        return m;
    }

    /**
     * Lấy list report transaction
     *
     * @return danh sách transaction bị report
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "/getReportDetail", method = RequestMethod.GET)
    public String getAllReportTransaction() throws JsonProcessingException {
        List<TransactionDetailDTO> dtos = transactionService.getAllReportDetail();
        return new Gson().toJson(dtos);
    }

    /**
     * Cập nhập transaction từ ERROR thành NOT PAY
     *
     * @param transaction bị report
     * @return danh sách transaction bị report
     */
    @RequestMapping(value = "/updateReport", method = RequestMethod.POST)
    public String updateReport(@RequestBody Transaction transaction) {
        boolean isSuccessful = false;

        TransactionDTO transactionDTO = transactionService.updateReport(transaction);
        if (transactionDTO != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Xóa report đã xử lý
     *
     * @param transaction bị report
     * @return danh sách transaction bị report
     */
    @RequestMapping(value = "/deleteReport", method = RequestMethod.POST)
    public String deleteReport(@RequestBody Transaction transaction) {
        boolean isSuccessful;
        isSuccessful = transactionService.deleteReport(transaction.getId());
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Xử lý Transaction bị report Thêm hình ảnh vào Transaction đúng Xóa
     * Transacion sai (ERROR)
     *
     * @param transId Transaction đúng
     * @param transIdError Transaction sai (ERROR)
     * @return Kết quả thực hiện
     */
    @RequestMapping(value = "/resolveReport")
    public String resolveReport(@RequestParam(name = "transId") String transId,
            @RequestParam(name = "transIdError") String transIdError) {

        boolean isSuccessful;
        isSuccessful = transactionService.resolveReport(transId, transIdError);
        if (isSuccessful) {
            return "success";
        }
        return "fail";
    }

    /**
     * Xác nhận Report sai Cập nhật transaction ERROR thành NOT PAY với biển số
     * xe phù hợp
     *
     * @param transId Transaction ID
     * @param licensePlate biển số xe đúng
     * @return Kết quả thực hiện
     */
    @RequestMapping(value = "/confirmReport")
    public String confirmReport(@RequestParam(name = "transId") String transId,
            @RequestParam(name = "licensePlate") String licensePlate) {

        boolean isSuccessful;
        isSuccessful = transactionService.confirmReport(transId, licensePlate);
        if (isSuccessful) {
            return "success";
        }
        return "fail";
    }
}
