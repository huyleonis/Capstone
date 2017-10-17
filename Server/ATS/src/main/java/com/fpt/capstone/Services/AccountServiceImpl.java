/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Repositories.AccountRepos;
import com.fpt.capstone.Repositories.PriceRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepos accountRepos;
    @Autowired
    private PriceRepos priceRepos;
    
    /**
     * Thực hiện trừ tiền vào tài khoản của account
     * @param username
     * @param stationId
     * @return 
     */
    @Override
    public String makePayment(String username, int stationId) {
        String result = "";
        Account accountEntity = accountRepos.getAccount(username);
        if (accountEntity == null) {
            return "Tài khoản không tồn tại";
        }
        AccountDTO accountDto = AccountDTO.convertFromEntity(accountEntity);
                
        Price priceEntity = priceRepos.findPriceByStationAndUsername(username, stationId);
        if (priceEntity == null) {
            return "Không tìm được giá";
        }
        PriceDTO priceDto = PriceDTO.convertFromEntity(priceEntity);
        
        if (accountDto.getBalance() < priceDto.getPrice()) {
            return "Tài khoản không đủ thực hiện trả phí";
        } else {
            double newBalance = accountDto.getBalance() - priceDto.getPrice();
            int sqlResult = accountRepos.updateBalance(accountDto.getId(), newBalance);
            if (sqlResult <= 0) {
                return "Không thể thực hiện thahh toán phí - Lỗi hệ thống";
            }
        }
        
        return result;
    }

    @Override
    public AccountDTO getAccount(String username) {
        Account accountEntity = accountRepos.getAccount(username);
        if (accountEntity != null) {
            AccountDTO dto = AccountDTO.convertFromEntity(accountEntity);
            return dto;
        }
        return null;
    }
<<<<<<< HEAD
    
=======

    public Account checkLoginFromDesktopApp(String username, String password) {
        return Account.convertFromEntity(accountRepos.checkLoginFromDesktopApp(username, password));
    }
>>>>>>> origin/CapstoneProjectOfHieu
}
