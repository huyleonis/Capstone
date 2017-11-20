/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topup")
public class TopupController {
    
    @RequestMapping(value = "mobicard", method = RequestMethod.POST, headers = "Content-Type=application/x-www-form-urlencoded")
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
        result.put("result", "false");
        result.put("message", "Not yet implemented");
        return result;
    }
}
