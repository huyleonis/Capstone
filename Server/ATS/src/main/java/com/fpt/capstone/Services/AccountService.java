/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.AccountDTO;
import java.util.List;

/**
 *
 * @author hp
 */
public interface AccountService {

    public String makePayment(String username, int priceId);        
 
    public AccountDTO getAccount(String username);        

    AccountDTO getAccountById(int id);
    
    AccountDTO checkLoginFromDesktopApp(String username, String password);
    
    public List<AccountDTO> getListAccount();

}
