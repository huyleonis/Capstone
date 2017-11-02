package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Account;

public class AccountDTO {
	private int id;
	private String username;
	private String password;
	private int role;
	private String fullname;
	private String email;
	private String phone;
	private String numberId;
	private String eWallet;
	private int vehicleId;
	private double balance;
	private boolean isActive;
	private boolean isEnable;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNumberId() {
		return numberId;
	}

	public void setNumberId(String numberId) {
		this.numberId = numberId;
	}

	public String geteWallet() {
		return eWallet;
	}

	public void seteWallet(String eWallet) {
		this.eWallet = eWallet;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public static AccountDTO convertFromEntity(Account account) {
		AccountDTO dto = new AccountDTO();

		dto.setId(account.getId());
		dto.setUsername(account.getUsername());
		dto.setPassword(account.getPassword());
		dto.setRole(account.getRole());
		dto.setFullname(account.getFullname());
		dto.setEmail(account.getEmail());
		dto.setPhone(account.getPhone());
		dto.setNumberId(account.getNumberId());
		dto.seteWallet(account.geteWallet());
		if (account.getVehicle() != null) {
			dto.setVehicleId(account.getVehicle().getId());
		}
		if (account.getBalance() != null) {
			dto.setBalance(account.getBalance());
		}
		dto.setActive((account.getActive() == 1) ? true : false);
		dto.setEnable((account.getEnable() == 1) ? true : false);

		return dto;
	}
}