/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.dtos;

import java.sql.Date;

/**
 *
 * @author Chi Hieu
 */
public class TransactionDTO {

    private String id;
    private int vehicleId;
    private String licensePlate;
    private int stationId;
    private String stationName;
    private java.util.Date dateTime;
    private String status;
    private int priceId;
    private Double price;
    private int laneId;
    private String laneName;
    private String type;
    private String photo;

    public TransactionDTO() {
    }

    public TransactionDTO(String id, int vehicleId, String licensePlate, int stationId, java.util.Date dateTime, String status, int priceId, int laneId, String type, String photo) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.stationId = stationId;
        this.dateTime = dateTime;
        this.status = status;
        this.priceId = priceId;
        this.laneId = laneId;
        this.type = type;
        this.photo = photo;
        this.licensePlate = licensePlate;
    }

    public TransactionDTO(String id, int vehicleId, String licensePlate, int stationId, String stationName, java.util.Date dateTime, String status, int priceId, Double price, int laneId, String laneName, String type, String photo) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.stationId = stationId;
        this.stationName = stationName;
        this.dateTime = dateTime;
        this.status = status;
        this.priceId = priceId;
        this.price = price;
        this.laneId = laneId;
        this.laneName = laneName;
        this.type = type;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public java.util.Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(java.util.Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
