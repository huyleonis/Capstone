package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Utils.TransactionStatus;
import com.fpt.capstone.Utils.TransactionType;

import java.util.Date;

public class TransactionDTO {

    private String id;
    private int vehicleId;
    private int stationId;
    private Date dateTime;
    private TransactionStatus status;
    private double price;
    private int laneId;
    private TransactionType type;
    private String photo;
    private String failReason;

    public TransactionDTO() {
    }

    public TransactionDTO(String id, int vehicleId, int stationId, Date dateTime, TransactionStatus status,
                          double price, int laneId, TransactionType type, String photo, String failReason) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.stationId = stationId;
        this.dateTime = dateTime;
        this.status = status;
        this.price = price;
        this.laneId = laneId;
        this.type = type;
        this.photo = photo;
        this.failReason = failReason;
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

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public static TransactionDTO convertFromEntity(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        convertFromEntity(transaction, dto);
        return dto;
    }
    
    public static void convertFromEntity(Transaction transaction, TransactionDTO dto) {        
        if (dto == null) {
            dto = new TransactionDTO();
        }
        
        dto.setId(transaction.getId());
        dto.setVehicleId(transaction.getVehicle().getId());
        dto.setStationId(transaction.getStation().getId());
        dto.setDateTime(transaction.getDateTime());                
        dto.setPrice(transaction.getPrice());
        if (transaction.getLane() != null) {
            dto.setLaneId(transaction.getLane().getId());
        }
        if (transaction.getType() == 1) {
            dto.setType(TransactionType.AUTOMATION);
        } else {
            dto.setType(TransactionType.MANUAL);
        }
        if (transaction.getPhoto() != null) {
            dto.setPhoto(transaction.getPhoto());
        }
        
        for (TransactionStatus value : TransactionStatus.values()) {
            if (value.getName().equals(transaction.getStatus())) {
                dto.setStatus(value);
                break;
            }
        }
        
    }
}
