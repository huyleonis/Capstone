package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;

import java.util.Date;

public class TransactionDTO {
    private String id;
    private String username;
    private int username_id;
    private int station_id;
    private Date date_time;
    private String vehicle_id;
    private String status;
    private int priceid;
    private double price_id;
    private String license_plate;

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    private int laneId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

        String id = transaction.getId();
        String username = transaction.getAccount().getUsername();
        int username_id = transaction.getAccount().getId();
        int station_id = transaction.getStation().getId();
        Date date_time = transaction.getDateTime();

        String vehicle_id = transaction.getPrice().getVehicletype().getName();
        String status = transaction.getStatus();
        int price_id = transaction.getPrice().getId();
        double price = transaction.getPrice().getPrice();
        if (transaction.getLane() != null) {
            int laneId = transaction.getLane().getId();
            dto.setLaneId(laneId);
        }


        dto.setId(id);
        dto.setUsername(username);
        dto.setUsername_id(username_id);
        dto.setStation_id(station_id);
        dto.setDate_time(date_time);
        dto.setVehicle_id(vehicle_id);
        dto.setStatus(status);
        dto.setPrice_id(price);
        dto.setPriceid(price_id);


        return dto;
    }

    public static Transaction convertToEntity(TransactionDTO transactionDTO){
        String id = transactionDTO.getId();
        int username_id = transactionDTO.getUsername_id();
        int station_id = transactionDTO.getStation_id();
        Date date_time = transactionDTO.getDate_time();
        String status = transactionDTO.getStatus();
        int price_id = transactionDTO.getPriceid();

        Transaction tr = new Transaction();
        tr.setId(id);
        tr.setDateTime(date_time);
        tr.setStatus(status);

        return tr;

    }
}