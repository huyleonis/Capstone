package com.fpt.capstone.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    private String id;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    private String photo;

    private String status;

    private byte type;

    //bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "username_id")
    private Account account;

    //bi-directional many-to-one association to Lane
    @ManyToOne
    private Lane lane;

    //bi-directional many-to-one association to Price
    @ManyToOne
    private Price price;

    //bi-directional many-to-one association to Station
    @ManyToOne
    private Station station;

    public Transaction() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Lane getLane() {
        return this.lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
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
