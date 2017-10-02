package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.AccountEntity;

public interface AccountService {
    AccountDTO login(AccountEntity account);
}
