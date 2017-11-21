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
    private boolean isActive;
    private boolean isEnable;
    private String licensePlate;
    private String licenseId;

    public AccountDTO() {
    }

    public AccountDTO(int id, String username, String password, int role, String fullname, String email, String phone,
            String numberId, String eWallet, int vehicleId, double balance, boolean isActive, boolean isEnable) {
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
        this.isActive = isActive;
        this.isEnable = isEnable;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
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
        dto.setActive(account.getActive());
        dto.setEnable(account.getEnable());

        return dto;
    }
}
