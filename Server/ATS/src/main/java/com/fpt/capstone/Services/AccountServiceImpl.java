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
import com.fpt.capstone.Entities.Vehicle;
import com.fpt.capstone.Repositories.AccountRepos;
import com.fpt.capstone.Repositories.PriceRepos;
import com.fpt.capstone.Repositories.VehicleRepos;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private AccountRepos accountRepos;

	@Autowired
	private PriceRepos priceRepos;

	@Autowired
	private VehicleRepos vehicleRepos;

	/**
	 * Thực hiện trừ tiền vào tài khoản của account
	 * 
	 * @param username
	 * @param stationId
	 * @return
	 */
	@Override
	public String makePayment(String username, int stationId) {
		String result = "";
		Account accountEntity = accountRepos.findByUsername(username);
		if (accountEntity == null) {
			return "Tài khoản [" + username + "] không tồn tại";
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
	public AccountDTO getAccountByUsername(String username) {
		Account accountEntity = accountRepos.findByUsername(username);
		if (accountEntity != null) {
			AccountDTO dto = AccountDTO.convertFromEntity(accountEntity);
			return dto;
		}
		return null;
	}

	@Override
	public AccountDTO checkLoginFromDesktopApp(String username, String password) {
		return AccountDTO.convertFromEntity(accountRepos.checkLoginFromDesktopApp(username, password));
	}

	@Override
	public AccountDTO getAccountById(int id) {
		Account account = accountRepos.findOne(id);
		if (account != null) {
			return AccountDTO.convertFromEntity(account);
		}
		return null;
	}

	@Override
	public List<AccountDTO> getListAccount() {
		List<Account> list = accountRepos.findAll();
		List<AccountDTO> result = new ArrayList<>();
		for (Account acc : list) {
			AccountDTO dto = AccountDTO.convertFromEntity(acc);
			result.add(dto);
		}
		return result;
	}

	@Override
	public AccountDTO insert(Account account) {

		AccountDTO dto = null;

		try {
			// neu user dang ki xe ma xe da co trong he thong
			if (account.getVehicle() != null) {
				Vehicle vehicle = vehicleRepos.findByLicensePlate(account.getVehicle().getLicensePlate());
				if (vehicle != null) {
					account.setVehicle(vehicle);
				}
			}

			Account processedAccount = accountRepos.save(account);
			if (processedAccount != null) {
				dto = AccountDTO.convertFromEntity(processedAccount);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dto;
	}

	@Override
	public AccountDTO update(Account account) {
		AccountDTO dto = null;

		try {
			Account existingAccount = accountRepos.findOne(account.getId());

			if (existingAccount != null) {
				account.setPassword(existingAccount.getPassword());
				
				// neu user dang ki xe ma xe da co trong he thong
				if (account.getVehicle() != null) {
					Vehicle vehicle = vehicleRepos
							.findByLicensePlate(account.getVehicle().getLicensePlate());
					if (vehicle != null) {
						account.setVehicle(vehicle);
					}
				}
				
				Account processedAccount = accountRepos.save(account);
				dto = AccountDTO.convertFromEntity(processedAccount);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dto;
	}

}
