package com.fpt.capstone.Entities;

import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "vehicleid")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "stationid")
    private Station station;

    @Column(name = "createdtime")
    private Date dateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laneid")
    private Lane lane;

    @Column(name = "type")
    private int type;

    @Column(name = "photo")
    private String photo;

    public Transaction() {        
    }

    public Transaction(String id, Vehicle vehicle, Station station, Date dateTime,
                       String status, double price, Lane lane, int type, String photo) {
        this.id = id;
        this.vehicle = vehicle;
        this.station = station;
        this.dateTime = dateTime;
        this.status = status;
        this.price = price;
        this.lane = lane;
        this.type = type;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
