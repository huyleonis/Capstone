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

    @RequestMapping(value = "getByDate/{vehicleId}/{fromDate}/{toDate}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDTO> getHistoryTransaction(@PathVariable String vehicleId,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate){

        List<TransactionDTO> result =
                transactionServiceImpl.getHistoryTransaction(vehicleId, fromDate, toDate);

        System.out.println("Get history from date " + fromDate + " to date: "
                + toDate + " result : " + result.size() + "VehicleId: " + vehicleId);
        return result;
    }
}
