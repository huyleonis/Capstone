package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;
import java.util.Date;

public class TransactionDTO {

    private String id;
    
    private Date dateTime;
    private String status;    
    private double price;
    private String failReason;
    private Integer laneId;
    private int stationId; 
    private String stationName;
    
    public TransactionDTO() {
    }
    
    public TransactionDTO(String id, Date dateTime, String status, double price, 
            String failReason, int laneId, int stationId) {
        this.id = id;
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
    
    public int getStationId() {
        return stationId;
    }
    
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }    
    
    public Integer getLaneId() {
        return laneId;
    }
    
    public void setLaneId(Integer laneId) {
        this.laneId = laneId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
        
    public static TransactionDTO convertFromEntity(Transaction tran) {
        TransactionDTO dto = new TransactionDTO();
        
        dto.setId(tran.getId());
        dto.setDateTime(tran.getDateTime());
        if (tran.getLane() != null) {
            dto.setLaneId(tran.getLane().getId());
        }
        if (tran.getStation() != null) {
            dto.setStationId(tran.getStation().getId());
            dto.setStationName(tran.getStation().getName());
        }
        if (tran.getPrice() != null) {
            dto.setPrice(tran.getPrice().getPrice());
        }
        
        dto.setStatus(tran.getStatus());        

        return dto;
    }
    
}
