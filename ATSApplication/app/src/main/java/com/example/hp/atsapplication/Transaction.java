package com.example.hp.atsapplication;

import java.util.Date;

/**
 * Created by tinpb on 10/11/2017.
 */

public class Transaction {
    private Long dateTime;
//    private String stationName;
    private int stationId;
    private double price;
    private String status;

//    public Transaction(Long dateTime, int stationId, double price, String status) {
//        this.dateTime = dateTime;
//        this.stationId = stationId;
//        this.price = price;
//        this.status = status;
//    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

//    public String getStationName() {
//        return stationName;
//    }
//
//    public void setStationName(String stationName) {
//        this.stationName = stationName;
//    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
