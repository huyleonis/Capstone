/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon.fpt.ktk.controller;

import hackathon.fpt.ktk.dto.AccountDTO;
import hackathon.fpt.ktk.paymentmodule.MobileCard;
import hackathon.fpt.ktk.paymentmodule.MobileCardResult;
import hackathon.fpt.ktk.paymentmodule.OrderProcessor;
import hackathon.fpt.ktk.service.AccountService;
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

    @RequestMapping(value = "bank", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object topupByBank(@RequestParam(name = "username") String username,
            @RequestParam(name = "amount") int amount) {
        Map<String, String> result = new HashMap<>();

        AccountDTO account = accountService.getAccountByUsername(username);

        if (account == null) {
            return null;
        }

        String fullname = account.getFullname();
        String email = account.getEmail();
        String phone = account.getPhone();
        String address = "";
        String orderCode = new Date().getTime() + "";

        try {
            String response = OrderProcessor.sendOrder(orderCode, amount + "", "", phone, email, address);
            result.put("result", "true");
            result.put("message", response);
        } catch (IOException ex) {
            Logger.getLogger(TopupController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            result.put("result", "false");
            result.put("message", ex.getMessage());
        }

        return result;
    }
    
    @RequestMapping(value = "checkOrder", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Object checkOrder(@RequestParam(name = "tokenCode") String tokenCode) {
        Map<String, String> result = new HashMap<>();
        try {
            String response = OrderProcessor.checkOrder(tokenCode);
            result.put("result", "true");
            result.put("message", response);
        } catch (IOException ex) {
            Logger.getLogger(TopupController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            result.put("result", "false");
            result.put("message", ex.getMessage());
        }
        return result;
    }
}