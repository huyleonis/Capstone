package com.fpt.capstone.Entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Transient
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private int role;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "numberid")
    private String numberId;

    @Column(name = "ewallet")
    private String eWallet;
    
    @Column(name = "loginstatus")
    private Boolean loginStatus;
    
    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name = "vehicleid")
    private Vehicle vehicle;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "isenable")
    private Boolean isEnabled;

    public Account() {
    }

    public Account(String username, String password, int role, String fullname, String email, String phone,
                   String numberId, String eWallet, Vehicle vehicle, Double balance, Boolean isActive, Boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.numberId = numberId;
        this.eWallet = eWallet;
        this.vehicle = vehicle;
        this.balance = balance;
        this.isActive = isActive;
        this.isEnabled = isEnabled;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getEnable() {
        return isEnabled;
    }

    public void setEnable(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
        
}
