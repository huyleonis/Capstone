package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Account;

public class AccountDTO {

    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_STAFF = "Staff";
    private static final String ROLE_DRIVER = "Driver";
    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "Inactive";
    private static final String ENABLE = "Enable";
    private static final String DISABLE = "Disable";

    private int id;
    private String username;
    private String password;
    private String role;
    private String fullname;
    private String email;
    private String phone;
    private String numberId;
    private String eWallet;
    private int vehicleId;
    private double balance;
    private String active;
    private String enable;
    private String licensePlate;
    private int vehicletypeId;

    public AccountDTO() {
    }

    public AccountDTO(int id, String username, String password, String role, String fullname, String email,
                      String phone, String numberId, String eWallet, int vehicleId, double balance, String active, String enable,
                      String licensePlate, int vehicletypeId) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
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

        switch (account.getRole()) {
            case 1:

                dto.setRole(ROLE_ADMIN);
                break;
            case 2:

                dto.setRole(ROLE_STAFF);
                break;
            case 3:

                dto.setRole(ROLE_DRIVER);
                break;

            default:
                break;
        }

        dto.setFullname(account.getFullname());
        if (account.getEmail() != null) {
            dto.setEmail(account.getEmail());
        } else {
            dto.setEmail("N/A");
        }

        if (account.getPhone() != null) {
            dto.setPhone(account.getPhone());
        } else {
            dto.setPhone("N/A");
        }

        if (account.getNumberId() != null) {
            dto.setNumberId(account.getNumberId());
        } else {
            dto.setNumberId("N/A");
        }
        dto.seteWallet(account.geteWallet());

        if (account.getVehicle() != null) {
            dto.setVehicleId(account.getVehicle().getId());
            dto.setLicensePlate(account.getVehicle().getLicensePlate());
            if (account.getVehicle().getVehicleType() != null) {
                dto.setVehicletypeId(account.getVehicle().getVehicleType().getId());
            } else {
                dto.setVehicletypeId(0);
            }
        } else {
            dto.setVehicleId(0);
            dto.setLicensePlate("N/A");
        }

        if (account.getBalance() != null) {
            dto.setBalance(account.getBalance());
        } else {
            dto.setBalance(0);
        }

        dto.setActive((account.getActive()) ? ACTIVE : INACTIVE);
        dto.setEnable((account.getEnable()) ? ENABLE : DISABLE);

        return dto;
    }
}
