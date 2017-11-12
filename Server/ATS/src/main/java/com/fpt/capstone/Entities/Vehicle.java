package com.fpt.capstone.Entities;

import javax.persistence.*;

@Entity(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "licenseplate")
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "typeid")
    private VehicleType vehicleType;

    @OneToOne(mappedBy = "vehicle")
    private Account account;

    public Vehicle() {
    }

    public Vehicle(String licensePlate, VehicleType vehicleType, Account account) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
