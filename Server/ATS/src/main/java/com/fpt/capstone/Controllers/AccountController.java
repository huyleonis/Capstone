package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.AccountEntity;
import com.fpt.capstone.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    protected AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public AccountDTO login(AccountEntity accountEntity){
        accountEntity = new AccountEntity();
        accountEntity.setUsername("son");
        accountEntity.setPassword("son");
        AccountDTO accountDTO = accountService.login(accountEntity);

        return accountDTO;
    }
}
