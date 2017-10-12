package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Services.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @RequestMapping(value = "getByDate/{username}/{fromDate}/{toDate}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDTO> getHistoryTransaction(@PathVariable String username, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate){

        List<TransactionDTO> result = transactionServiceImpl.getHistoryTransaction(username, fromDate, toDate);

        System.out.println("Get history from date " + fromDate + " to date: " + toDate + " result : " + result.size() + "username: " + username);
        return result;
    }
}