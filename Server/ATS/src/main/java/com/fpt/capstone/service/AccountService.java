package com.fpt.capstone.service;

import com.fpt.capstone.dto.AccountDTO;
import com.fpt.capstone.entity.Account;

import java.util.List;

public interface AccountService {

    AccountDTO login(Account account);

    AccountDTO insert(Account account);

    AccountDTO update(Account account);

    boolean checkAccountBalance(double priceToBeChecked, int accountId);

    int updateAccountBalance(double amountToBeDeducted, int accountId);

    AccountDTO getAccountById(int id);

    List<AccountDTO> getAllAccount();

}
