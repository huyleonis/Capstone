package com.fpt.capstone.Dtos;

public class Account {
    private String username;
    private String password;
    private int role;
    private String numberId;
    private String licensePlate;

    public Account() {
    }

    public Account(String username, String password, int role, String numberId, String licensePlate) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.numberId = numberId;
        this.licensePlate = licensePlate;
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
}
