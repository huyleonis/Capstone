/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon.fpt.ktk.controller;

import hackathon.fpt.ktk.dto.TransactionDetailDTO;
import hackathon.fpt.ktk.service.TransactionServiceImpl;
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

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @RequestMapping(value = "getByDate/{username}/{fromDate}/{toDate}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDetailDTO> getHistoryTransaction(@PathVariable String username,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate) {

        List<TransactionDetailDTO> result
                = transactionServiceImpl.getHistoryTransaction(username, fromDate, toDate);

        System.out.println("Get history from date " + fromDate + " to date: "
                + toDate + " result : " + result.size() + "username: " + username);
        return result;
    }
    
}
