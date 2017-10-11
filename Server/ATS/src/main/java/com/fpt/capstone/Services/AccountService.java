package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.Account;

public interface AccountService {

    boolean checkAccountBalance(double priceToBeChecked, int accountId);

    int updateAccountBalance(double amountToBeDeducted, int accountId);

    Account getAccountById(int id);
}
