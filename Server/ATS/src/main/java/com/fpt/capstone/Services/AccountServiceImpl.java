package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.Account;
import com.fpt.capstone.Repositories.AccountRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepos accountRepos;

    @Override
    public boolean checkAccountBalance(double priceToBeChecked, int accountId) {
        Account dto = Account.convertFromEntity(accountRepos.findAccountById(accountId));

        if ((dto.getBalance() - priceToBeChecked) >= 0) {
            return true;
        }

        return false;
    }

    @Override
    public int updateAccountBalance(double amountToBeDeducted, int accountId) {
        int isSuccessful = 0;

        Account dto = Account.convertFromEntity(accountRepos.findAccountById(accountId));

        if ((dto.getBalance() - amountToBeDeducted) >= 0) {
            isSuccessful = accountRepos.updateAccountBalance(dto.getBalance() - amountToBeDeducted, accountId);
            return isSuccessful;
        }

        return isSuccessful;
    }

    @Override
    public Account getAccountById(int id) {

        return Account.convertFromEntity(accountRepos.findAccountById(id));
    }
}
