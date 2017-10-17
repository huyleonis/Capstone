package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Account;

public class AccountDTO {
    private int id;
    private String username;
    private String fullname;
    private int role;
    private String numberId;
    private String licensePlate;
    private String email;
    private String phone;
    private String licenseId;   
    private String vehicleType;
    private double balance;

    public AccountDTO() {
    }

    public AccountDTO(int id, String username, int role, String numberId, String licensePlate, double balance) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.numberId = numberId;
        this.licensePlate = licensePlate;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }    

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }



    
    public static AccountDTO convertFromEntity(Account account){
        AccountDTO dto = new AccountDTO();
        
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        if (account.getVehicle() != null) {
            dto.setLicensePlate(account.getVehicle().getLicensePlate());
        }
        
        dto.setNumberId(account.getNumberId());
        dto.setBalance(account.getBalance());
        dto.setRole(account.getRole());
        dto.setPhone(account.getPhone());
        dto.setEmail(account.getEmail());
        dto.setBalance(account.getBalance());
        dto.setLicenseId("123-456-xxx");        
        dto.setFullname(account.getFullname());
        if (account.getVehicle() != null) {
            dto.setVehicleType(account.getVehicle().getVehicletype().getName());
        }        
        return dto;
    }
}