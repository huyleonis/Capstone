package com.fpt.capstone.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "price")
public class Price implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="fromdate")
    private Date fromDate;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "stationid")
    private Station station;

    @ManyToOne
    @JoinColumn(name="typeid")
    private VehicleType vehicleType;

    @Column(name = "isactive")
    private Boolean isActive;

    @OneToMany(mappedBy="price")
    private List<Transaction> transactions;

    public Price() {
    }

    public Price(Date fromDate, double price, Station station, VehicleType vehicleType, Boolean isActive, List<Transaction> transactions) {
        this.fromDate = fromDate;
        this.price = price;
        this.station = station;
        this.vehicleType = vehicleType;
        this.isActive = isActive;
        this.transactions = transactions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
