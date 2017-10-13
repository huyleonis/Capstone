package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    private String id;

    @Column(name = "date_time")
    private Date dateTime;

    @Column(name = "photo")
    private String photo;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private byte type;

    //bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "username_id")
    private Account account;

    //bi-directional many-to-one association to Lane
    @ManyToOne
    @JoinColumn(name = "lane_id")
    private Lane lane;

    //bi-directional many-to-one association to Price
    @ManyToOne
    @JoinColumn(name = "price_id")
    private Price price;

    //bi-directional many-to-one association to Station
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
