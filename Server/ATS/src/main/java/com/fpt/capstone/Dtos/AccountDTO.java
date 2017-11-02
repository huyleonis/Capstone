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
    private Double balance;
    private boolean isActive;
    private boolean isEnable;

    public AccountDTO() {
    }

    public AccountDTO(int id, String username, String fullname, int role, String numberId, String licensePlate, String email, String phone, String licenseId, String vehicleType, Double balance, boolean isActive, boolean isEnable) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.numberId = numberId;
        this.licensePlate = licensePlate;
        this.email = email;
        this.phone = phone;
        this.licenseId = licenseId;
        this.vehicleType = vehicleType;
        this.balance = balance;
        this.isActive = isActive;
        this.isEnable = isEnable;
    }

    public AccountDTO(int id, String username, int role, String numberId, String licensePlate, Double balance) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.numberId = numberId;
        this.licensePlate = licensePlate;
        this.balance = balance;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
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
        if (account.getVehicleId() != null) {
            dto.setLicensePlate(account.getVehicleId().getLicensePlate());
        }
        
        dto.setNumberId(account.getNumberId());
        dto.setBalance(account.getBalance());
        dto.setRole(account.getRole());
        dto.setPhone(account.getPhone());
        dto.setEmail(account.getEmail());
        if(account.getBalance() != null){
            account.setBalance(account.getBalance());
        }else{
            account.setBalance(0.0);
        }
        dto.setBalance(account.getBalance());
        if(account.getVehicleId() != null){
            dto.setLicenseId(account.getVehicleId().getLicensePlate());
        }else{
             dto.setLicenseId(null);   
        }
            
        dto.setFullname(account.getFullname());
        if (account.getVehicleId()!= null) {
            dto.setVehicleType(account.getVehicleId().getTypeId().getName());
        }
        dto.setIsActive(account.getIsActive());
        dto.setIsEnable(account.getIsEnable());
        return dto;
    }
}