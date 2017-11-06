package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Account;

public class AccountDTO {

	private static final String ROLE_ADMIN = "Admin";
	private static final String ROLE_STAFF = "Staff";
	private static final String ROLE_DRIVER = "Driver";

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
	private int active;
	private int enable;
	private String licensePlate;
	private int vehicletypeId;

	public AccountDTO() {
	}

	public AccountDTO(int id, String username, String password, int role, String fullname, String email, String phone,
			String numberId, String eWallet, int vehicleId, double balance, int active, int enable, String licensePlate,
			int vehicletypeId) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.numberId = numberId;
		this.eWallet = eWallet;
		this.vehicleId = vehicleId;
		this.balance = balance;
		this.active = active;
		this.enable = enable;
		this.licensePlate = licensePlate;
		this.vehicletypeId = vehicletypeId;
	}

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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getVehicletypeId() {
		return vehicletypeId;
	}

	public void setVehicletypeId(int vehicletypeId) {
		this.vehicletypeId = vehicletypeId;
	}

	public static AccountDTO convertFromEntity(Account account) {
		AccountDTO dto = new AccountDTO();

		dto.setId(account.getId());
		dto.setUsername(account.getUsername());
		dto.setPassword(account.getPassword());
		// switch (account.getRole()) {
		// case 1:
		//
		// dto.setRole(ROLE_ADMIN);
		// break;
		// case 2:
		//
		// dto.setRole(ROLE_STAFF);
		// break;
		// case 3:
		//
		// dto.setRole(ROLE_DRIVER);
		// break;
		//
		// default:
		// break;
		// }
		dto.setRole(account.getRole());
		dto.setFullname(account.getFullname());
		dto.setEmail(account.getEmail());
		dto.setPhone(account.getPhone());
		dto.setNumberId(account.getNumberId());
		dto.seteWallet(account.geteWallet());
		if (account.getVehicle() != null) {
			dto.setVehicleId(account.getVehicle().getId());
			dto.setLicensePlate(account.getVehicle().getLicensePlate());
			if (account.getVehicle().getVehicletype() != null) {
				dto.setVehicletypeId(account.getVehicle().getVehicletype().getId());
			} else {
				dto.setVehicletypeId(0);
			}
		} else {
			dto.setVehicleId(0);
			dto.setLicensePlate("");
		}
		if (account.getBalance() != null) {
			dto.setBalance(account.getBalance());
		} else {
			dto.setBalance(0);
		}
		dto.setActive(account.getActive());
		dto.setEnable(account.getEnable());

		return dto;
	}
}