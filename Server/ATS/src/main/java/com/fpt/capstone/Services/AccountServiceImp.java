package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.AccountEntity;
import com.fpt.capstone.Repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImp implements AccountService{

    @Autowired
    protected AccountRepo accountRepo;

    @Override
    public AccountDTO login(AccountEntity account){
        AccountDTO dto = null;
        AccountEntity loginUser = accountRepo.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (loginUser !=null){
            dto = AccountDTO.convertFromEntity(loginUser);
        }
        return dto;
    }
}
