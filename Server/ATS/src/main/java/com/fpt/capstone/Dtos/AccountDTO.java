package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.AccountEntity;

public class AccountDTO {
    private String Username;
    private String Password;
    private Integer Role;
    private String NumberId;
    private String LicensePlate;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getRole() {
        return Role;
    }

    public void setRole(Integer role) {
        Role = role;
    }

    public String getNumberId() {
        return NumberId;
    }

    public void setNumberId(String numberId) {
        NumberId = numberId;
    }

    public String getLicensePlate() {
        return LicensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        LicensePlate = licensePlate;
    }

    public static AccountDTO convertFromEntity(AccountEntity account){
        AccountDTO dto = new AccountDTO();

        if (account.getUsername() != null){
            dto.setUsername(account.getUsername());
        }
        if (account.getPassword() != null){
            dto.setPassword(account.getPassword());
        }
        if (account.getRole() != null){
            dto.setRole(account.getRole());
        }
        if (account.getNumberId() != null){
            dto.setNumberId(account.getNumberId());
        }
//        if (account.getVehicle().getLicensePlate() != null){
//            dto.setLicensePlate(account.getVehicle().getLicensePlate());
//        }

        return dto;
    }
}
