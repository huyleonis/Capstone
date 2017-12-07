/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.dtos;

import java.util.Date;

/**
 *
 * @author Chi Hieu
 */
public class TransactionDetailDTO {
    private String licensePlate;
    private String id;
    private String staus;
    private String dateTime;
    private String type;
    private String photo;
    private String typeVehicle;
    private Double price;

    public TransactionDetailDTO() {
    }

    public TransactionDetailDTO(String licensePlate, String id, String staus, String dateTime, String type, String photo, String typeVehicle, Double price) {
        this.licensePlate = licensePlate;
        this.id = id;
        this.staus = staus;
        this.dateTime = dateTime;
        this.type = type;
        this.photo = photo;
        this.typeVehicle = typeVehicle;
        this.price = price;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}
