/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;
import java.util.Date;

/**
 *
 * @author hp
 */
public class TransactionDetailDTO {
    private String id;
    
    private Date dateTime;
    private String status;    
    private double price;
    private String failReason;
    
    private Integer laneId;
    private int stationId; 
    private String zone;
    
    private String stationName;
    private String username;
    private String licensePlate;
    private String vehicleType;
    private String type;
    public TransactionDetailDTO() {
    }

    public TransactionDetailDTO(String id, Date dateTime, String status, double price, String failReason, Integer laneId, int stationId, String zone, String stationName, String username, String licensePlate, String vehicleType, String type) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.price = price;
        this.failReason = failReason;
        this.laneId = laneId;
        this.stationId = stationId;
        this.zone = zone;
        this.stationName = stationName;
        this.username = username;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.type = type;
    }

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

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Integer getLaneId() {
        return laneId;
    }

    public void setLaneId(Integer laneId) {
        this.laneId = laneId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public static TransactionDetailDTO covertFromEntity(Transaction tran) {
        TransactionDetailDTO dto = new TransactionDetailDTO();
        
        dto.setId(tran.getId());
        dto.setDateTime(tran.getDateTime());
        if (tran.getLane() != null) {
            dto.setLaneId(tran.getLane().getId());
        }
        if (tran.getStation() != null) {
            dto.setStationId(tran.getStation().getId());
            dto.setStationName(tran.getStation().getName());
            dto.setZone(tran.getStation().getZone());
        }
        if (tran.getPrice() != null) {
            dto.setPrice(tran.getPrice().getPrice());
        }
        
        dto.setStatus(tran.getStatus()); 
        
        if (tran.getAccount() != null) {
            dto.setUsername(tran.getAccount().getUsername());
            if (tran.getAccount().getVehicle() != null) {
                dto.setLicensePlate(tran.getAccount().getVehicle().getLicensePlate());
                dto.setVehicleType(tran.getAccount().getVehicle().getVehicletype().getName());                
            }                        
        }
        
        dto.setType(tran.getType() == 1? "tự động" : "thủ công");
                
        return dto;
    }
    
}
