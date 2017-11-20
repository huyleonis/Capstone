/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.PaymentModule.MobileCard;
import com.fpt.capstone.PaymentModule.MobileCardResult;
import com.fpt.capstone.Services.AccountService;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topup")
public class TopupController {
    
    @Autowired
    private AccountService accountService;
    

    @RequestMapping(value = "mobicard", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object topupByMobicard(@RequestParam(name = "card") String card,
            @RequestParam(name = "cardNumber") String cardNumber,
            @RequestParam(name = "cardSeri") String cardSeri,
            @RequestParam(name = "username") String username) {

        
        System.out.println("Request Topup: "
                + "   - Card Number: " + cardNumber
                + "   - Card Seri: " + cardSeri
                + "   - Card Type: " + card);
        Map<String, String> result = new HashMap<>();

        MobileCard mc = new MobileCard();
        try {
            AccountDTO account = accountService.getAccountByUsername(username);
            
            MobileCardResult topUpResult = mc.cardPay(cardNumber, cardSeri, card, new Date().getTime() + "", 
                    account.getFullname(), account.getPhone(), account.getEmail());                       
            
            if (topUpResult.getErrorCode().equals("00")) {
                System.out.println("Success top up");
                boolean sqlResult = accountService.topupBalance(username, Double.parseDouble(topUpResult.getAmount()));
                
                System.out.println("Result update database: " + sqlResult);
                result.put("result", "true");
                result.put("message", topUpResult.getErrorMessage());                
            } else {
                System.out.println("Fail to top up, message: " + topUpResult.getErrorMessage());
                result.put("result", "false");
                result.put("message", topUpResult.getErrorMessage());
            }
        } catch (IOException ex) {
            Logger.getLogger(TopupController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            result.put("result", "false");
            result.put("message", ex.getMessage());
        }
        return result;
    }
}