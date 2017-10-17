/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Services.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chi Hieu
 */

@RestController
@RequestMapping("/login")
public class LoginDesktopAppController {
    
    @Autowired
    AccountServiceImpl accountServiceImpl;
    
    @RequestMapping(value = "/checkLogin/{username}/{password}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountDTO checkLogin(@PathVariable String username, @PathVariable String password) {
        
        return accountServiceImpl.checkLoginFromDesktopApp(username, password);
    }
}
