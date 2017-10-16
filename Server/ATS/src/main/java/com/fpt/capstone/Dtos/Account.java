package com.fpt.capstone.Dtos;

public class Account {

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

    public Account() {
    }

    public Account(int id, String username, String password, int role, String fullname, String email, String phone, String numberId, String eWallet, int vehicleId, double balance) {
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

    public static Account convertFromEntity(com.fpt.capstone.Entities.Account account) {
        Account dto = new Account();
        if (account != null) {
            if (account.getRole() == 1 || account.getRole() == 2) {
                dto.setRole(account.getRole());
                dto.setPassword(account.getPassword());
                dto.setUsername(account.getUsername());
                dto.setFullname(account.getFullname());
            } else if (account.getRole() == 3) {
                dto.setId(account.getId());
                dto.setNumberId(account.getNumberId());
                dto.setRole(account.getRole());
                dto.setPassword(account.getPassword());
                dto.setUsername(account.getUsername());
                dto.setEmail(account.getEmail());
                dto.setFullname(account.getFullname());
                dto.setPhone(account.getPhone());
                dto.setBalance(account.getBalance());
                dto.seteWallet(account.geteWallet());
                dto.setVehicleId(account.getVehicle().getId());
            }
        } else {
            dto = null;
        }
        return dto;
    }
}
