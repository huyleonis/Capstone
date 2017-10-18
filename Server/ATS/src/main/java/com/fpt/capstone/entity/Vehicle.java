package com.fpt.capstone.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable{

    @Id
    private int id;

    @Column(name="license_plate")
    private String licensePlate;

    //bi-directional many-to-one association to Account
    @OneToMany(mappedBy="vehicle")
    private List<Account> accounts;

    //bi-directional many-to-one association to Vehicletype
    @ManyToOne
    @JoinColumn(name="type_id")
    private Vehicletype vehicletype;

    public Vehicle() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account addAccount(Account account) {
        getAccounts().add(account);
        account.setVehicle(this);

        return account;
    }

    public Account removeAccount(Account account) {
        getAccounts().remove(account);
        account.setVehicle(null);

        return account;
    }

    public Vehicletype getVehicletype() {
        return this.vehicletype;
    }

    public void setVehicletype(Vehicletype vehicletype) {
        this.vehicletype = vehicletype;
    }
}
