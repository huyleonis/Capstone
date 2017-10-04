package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;

import java.util.Date;

public class TransactionDTO {
    private int id;
    private String username;
    private int username_id;
    private int station_id;
    private Date date_time;
    private String vehicle_id;
    private String status;
    private int priceid;
    private double price_id;
    private String license_plate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice_id() {
        return price_id;
    }

    public void setPrice_id(double price_id) {
        this.price_id = price_id;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public int getUsername_id() {
        return username_id;
    }

    public void setUsername_id(int username_id) {
        this.username_id = username_id;
    }

    public int getPriceid() {
        return priceid;
    }

    public void setPriceid(int priceid) {
        this.priceid = priceid;
    }

    public static TransactionDTO convertFromEntity(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();

        int id = transaction.getId();
        String username = transaction.getAccount().getUsername();
        int station_id = transaction.getStation().getId();
        Date date_time = transaction.getDateTime();
//        String vehicle_id = transaction.getVehicle().getLicensePlate();
        String status = transaction.getStatus();
        double price = transaction.getPrice().getPrice();

        dto.setId(id);
        dto.setUsername(username);
        dto.setStation_id(station_id);
        dto.setDate_time(date_time);
//        dto.setVehicle_id(vehicle_id);
        dto.setStatus(status);
        dto.setPrice_id(price);

        return dto;
    }


}
