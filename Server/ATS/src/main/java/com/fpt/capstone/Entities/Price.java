package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="from_date")
    private Date fromDate;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name="type_id")
    private VehicleType vehicleType;

    @Column(name = "is_active")
    private int active;

    @OneToMany(mappedBy="price")
    private List<Transaction> transactions;

    public Price() {
    }

    public Price(Date fromDate, double price, Station station, VehicleType vehicleType, int active, List<Transaction> transactions) {
        this.fromDate = fromDate;
        this.price = price;
        this.station = station;
        this.vehicleType = vehicleType;
        this.active = active;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
