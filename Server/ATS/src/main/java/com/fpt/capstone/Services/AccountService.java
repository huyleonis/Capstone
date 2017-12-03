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

	void insert(String username, String password, String email, String phone, String numberId, String licensePlate);

	AccountDTO update(Account account);

	String checkLogin(String username, String password);
        
	boolean checkLicensePlate(String username, String licensePlate);

	boolean updateToken(String username, String token);
        
	boolean topupBalance(String username, double amount);

	boolean active(Account account);

	boolean deactive(Account account);

        boolean checkUsername(String username);
}