package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Services.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @RequestMapping(value = "findByLicensePlate/{license_plate}/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO findByLicensePlate(@PathVariable String license_plate, @PathVariable int id){
        return transactionServiceImpl.findByLicensePlate(license_plate, id);
    }



}
