package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Services.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/transactiondetail")
public class TransactionDetailController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @RequestMapping(value = "/getTransactionDetail/{transactionId}", method = RequestMethod.GET)
    @ResponseBody
    public TransactionDetailDTO getTransactionDetail(@PathVariable String transactionId) {

        TransactionDetailDTO dto = transactionDetailService.findTransactionById(transactionId);

        return dto;
    }
}
