package com.fpt.capstone.Dtos;

import java.util.Date;

public class TransactionDetailDTO {

    private String transactionId;
    private String stationName;
    private String zone;
    private double price;
    private int stationId;
    private Date dateTime;
    private String status;

    public TransactionDetailDTO() {
    }

    public TransactionDetailDTO(String transactionId, String stationName, String zone, double price, int stationId, Date dateTime, String status) {
        this.transactionId = transactionId;
        this.stationName = stationName;
        this.zone = zone;
        this.price = price;
        this.stationId = stationId;
        this.dateTime = dateTime;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
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
}
