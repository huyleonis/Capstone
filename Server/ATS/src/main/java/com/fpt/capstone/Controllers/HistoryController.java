package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Services.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    /**
     * Tìm list những Transaction trong khoảng thời gian yêu cầu.
     *
     * @param username
     * @param fromDate
     * @param toDate
     * @return danh sách thông tin transaction tìm được
     */
    @RequestMapping(value = "getByDate/{username}/{fromDate}/{toDate}")
    @ResponseBody
    public List<TransactionDetailDTO> getHistoryTransaction(@PathVariable String username,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate) {

        List<TransactionDetailDTO> result
                = transactionServiceImpl.getHistoryTransaction(username, fromDate, toDate);

        System.out.println("Get history from date " + fromDate + " to date: "
                + toDate + " result : " + result.size() + "VehicleId: " + username);
        return result;
    }
}
