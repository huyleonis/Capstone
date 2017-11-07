package com.fpt.capstone.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Services.TransactionServiceImpl;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @RequestMapping(value = "getByDate/{username}/{fromDate}/{toDate}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDTO> getHistoryTransaction(@PathVariable String username, 
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate, 
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate){

        List<TransactionDTO> result = 
                transactionServiceImpl.getHistoryTransaction(username, fromDate, toDate);

        System.out.println("Get history from date " + fromDate + " to date: " 
                + toDate + " result : " + result.size() + "username: " + username);
        return result;
    }
}
