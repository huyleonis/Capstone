/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;



/**
 * @author hp
 */
public class TransactionDetailDTO extends TransactionDTO {

    private String laneName;
//    private String stationName;
    private String zone;
    private String location;
    private String username;
    private String licensePlate;
    private String vehicleType;
    

    public TransactionDetailDTO() {
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

//    public String getStationName() {
//        return stationName;
//    }
//
//    public void setStationName(String stationName) {
//        this.stationName = stationName;
//    }

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

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    

    public static TransactionDetailDTO covertFromEntity(Transaction tran) {
        TransactionDetailDTO dto = new TransactionDetailDTO();
        
        TransactionDTO.convertFromEntity(tran, dto);
        
        if (tran.getLane() != null) {
            dto.setLaneName(tran.getLane().getName());
        }
        if (tran.getStation() != null) {
            dto.setStationName(tran.getStation().getName());
            dto.setZone(tran.getStation().getZone());
            dto.setLocation(tran.getStation().getLocation());
        }
        
        if (tran.getVehicle() != null) {
            dto.setLicensePlate(tran.getVehicle().getLicensePlate());
            dto.setVehicleType(tran.getVehicle().getVehicleType().getName());
            dto.setUsername(tran.getVehicle().getAccount().getUsername());
        }
                
        return dto;
    }

}
