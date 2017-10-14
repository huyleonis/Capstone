package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;
import java.util.Date;



public class TransactionDTO {
    private String id;

    private String username;        
    private Date dateTime;
    private String status;   
    private double price;
    private String failReason;
    private Integer laneId;
    private int stationId;    
    private String vehicleType;


    public TransactionDTO() {
    }

    public TransactionDTO(String id, String username, Date dateTime, String status, double price, String failReason, int laneId, int stationId) {
        this.id = id;
        this.username = username;
        this.dateTime = dateTime;
        this.status = status;
        this.price = price;
        this.failReason = failReason;
        this.laneId = laneId;
        this.stationId = stationId;
    }

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

    public int getLaneId() {
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public static TransactionDTO convertFromEntity(Transaction tran) {
        TransactionDTO dto = new TransactionDTO();
        
        dto.setId(tran.getId());
        dto.setDateTime(tran.getDateTime());
        if (tran.getLane() != null) {
            dto.setLaneId(tran.getLane().getId());
        }
        if (tran.getStation()!= null) {
            dto.setStationId(tran.getStation().getId());
        }
        if (tran.getPrice() != null) {
            dto.setPrice(tran.getPrice().getPrice());
        }
        if (tran.getAccount() != null) {
            if (tran.getAccount().getVehicle() != null) {
                dto.setVehicleType(tran.getAccount().getVehicle().getVehicletype().getName());
            }            
        }
        
        dto.setStatus(tran.getStatus());
        dto.setUsername(tran.getAccount().getUsername());        
        
        return dto;
    }
    
}