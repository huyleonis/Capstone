package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_time")
    private Date dateTime;

//    @Column(name="license_plate")
//    private String licensePlate;

    private String status;

    //bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name="username_id")
    private Account account;

    //bi-directional many-to-one association to Price
    @ManyToOne
    private Price price;

    //bi-directional many-to-one association to Station
    @ManyToOne
    private Station station;

    public Transaction() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

//    public String getLicensePlate() {
//        return this.licensePlate;
//    }
//
//    public void setLicensePlate(String licensePlate) {
//        this.licensePlate = licensePlate;
//    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Station getStation() {
        return this.station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
