/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.Account;

import java.util.List;

/**
 *
 * @author hp
 */
public interface AccountService {

	String makePayment(String username, int stationId);

	AccountDTO getAccountByUsername(String username);

	AccountDTO getAccountById(int id);

	AccountDTO checkLoginFromDesktopApp(String username, String password);

	List<AccountDTO> getListAccount();

	AccountDTO insert(Account account);

	AccountDTO update(Account account);

	AccountDTO getAccountByTransactionId(String transactionId);
}
