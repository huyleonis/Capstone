package com.fpt.capstone.service;

import com.fpt.capstone.dto.AccountDTO;
import com.fpt.capstone.entity.Account;
import com.fpt.capstone.utils.SHAGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl extends AbstractServiceImpl implements AccountService {

    @Override
    public AccountDTO login(Account account) {

        AccountDTO accountDTO = null;
//        String hashedPassword = SHAGenerator.generateSHA256(account.getPassword().toLowerCase());
        Account loginAccount = accountRepos.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (loginAccount != null) {
            accountDTO = AccountDTO.convertFromEntity(loginAccount);
        }

        return accountDTO;
    }

    @Override
    public AccountDTO insert(Account account) {
        AccountDTO accountDTO = null;

//        String hashedPassword = SHAGenerator.generateSHA256(account.getPassword().toLowerCase());
//        account.setPassword(hashedPassword);
        Account processedAccount = accountRepos.save(account);
        if (processedAccount != null) {
            accountDTO = AccountDTO.convertFromEntity(processedAccount);
        }

        return accountDTO;
    }

    @Override
    public AccountDTO update(Account account) {
        AccountDTO accountDTO = null;

        Account existingAccount = accountRepos.findOne(account.getId());
        if (existingAccount != null) {
            Account processAccount = accountRepos.save(account);
            accountDTO = AccountDTO.convertFromEntity(processAccount);
        }

        return accountDTO;
    }

    @Override
    public boolean checkAccountBalance(double priceToBeChecked, int accountId) {
        AccountDTO dto = AccountDTO.convertFromEntity(accountRepos.findOne(accountId));

        if ((dto.getBalance() - priceToBeChecked) >= 0) {
            return true;
        }

        return false;
    }

    @Override
    public int updateAccountBalance(double amountToBeDeducted, int accountId) {
        int isSuccessful = 0;

        AccountDTO dto = AccountDTO.convertFromEntity(accountRepos.findOne(accountId));

        if ((dto.getBalance() - amountToBeDeducted) >= 0) {
            isSuccessful = accountRepos.updateAccountBalance(dto.getBalance() - amountToBeDeducted, accountId);
            return isSuccessful;
        }

        return isSuccessful;
    }

    @Override
    public AccountDTO getAccountById(int id) {

        Account account = accountRepos.findOne(id);
        if (account != null) {
            AccountDTO dto = AccountDTO.convertFromEntity(account);
            return dto;
        }

        return null;
    }

    @Override
    public List<AccountDTO> getAllAccount() {

        List<Account> accounts = accountRepos.findAll();
        List<AccountDTO> dtos = new ArrayList<>();
        for (Account account : accounts) {
            dtos.add(AccountDTO.convertFromEntity(account));
        }
        return dtos;
    }
}
