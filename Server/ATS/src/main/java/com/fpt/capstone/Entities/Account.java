package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    private int id;

    @Column(name="e_wallet")
    private String eWallet;

    private String email;

    private String fullname;

    @Column(name="number_id")
    private String numberId;

    private String password;

    private String phone;

    private int role;

    private String username;

    //bi-directional many-to-one association to Vehicle
    @ManyToOne
    private Vehicle vehicle;

    //bi-directional many-to-one association to Beacon
    @OneToMany(mappedBy="account")
    private List<Beacon> beacons;

    //bi-directional many-to-one association to Transaction
    @OneToMany(mappedBy="account")
    private List<Transaction> transactions;

    public Account() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEWallet() {
        return this.eWallet;
    }

    public void setEWallet(String eWallet) {
        this.eWallet = eWallet;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNumberId() {
        return this.numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Beacon> getBeacons() {
        return this.beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Beacon addBeacon(Beacon beacon) {
        getBeacons().add(beacon);
        beacon.setAccount(this);

        return beacon;
    }

    public Beacon removeBeacon(Beacon beacon) {
        getBeacons().remove(beacon);
        beacon.setAccount(null);

        return beacon;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Transaction addTransaction(Transaction transaction) {
        getTransactions().add(transaction);
        transaction.setAccount(this);

        return transaction;
    }

    public Transaction removeTransaction(Transaction transaction) {
        getTransactions().remove(transaction);
        transaction.setAccount(null);

        return transaction;
    }
}
