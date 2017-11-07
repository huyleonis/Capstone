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
    private String licensePlate;
    private String stationName;
    private Date dateTime;
    private String status;
    private Double price;
    private String laneName;
    private Boolean type;
    private String photo;

    public TransactionDTO() {
    }
    
    public TransactionDTO(String id, String licensePlate, String stationName, Date dateTime, String status, Double price, String laneName, Boolean type, String photo) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.stationName = stationName;
        this.dateTime = dateTime;
        this.status = status;
        this.price = price;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    
    
}
