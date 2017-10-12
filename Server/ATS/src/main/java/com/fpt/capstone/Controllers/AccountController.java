/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Repositories.AccountRepos;
import com.fpt.capstone.Services.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    
    /**
     * Get account info by username
     * @param username
     * @return 
     */
    @RequestMapping(value = "get/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountDTO getAccount(@PathVariable String username) {
        AccountDTO acc = accountServiceImpl.getAccount(username);
        
        return acc;
    }
}
