package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Price;

import java.util.Date;

public class PriceDTO {

    private int id;
    private String nameStation;
    private String locationStation;
    private String zoneStation;
    private double price;
    private String nameVehicleType;
    private Date fromDate;
    private int idStation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameStation() {
        return nameStation;
    }

    public void setNameStation(String nameStation) {
        this.nameStation = nameStation;
    }

    public String getLocationStation() {
        return locationStation;
    }

    public void setLocationStation(String locationStation) {
        this.locationStation = locationStation;
    }

    public String getZoneStation() {
        return zoneStation;
    }

    public void setZoneStation(String zoneStation) {
        this.zoneStation = zoneStation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNameVehicleType() {
        return nameVehicleType;
    }

    public void setNameVehicleType(String nameVehicleType) {
        this.nameVehicleType = nameVehicleType;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }
        

    public static PriceDTO convertFromEntity(Price price){
        PriceDTO dto = new PriceDTO();

        dto.setId(price.getId());
        if (price.getStation() != null) {
            dto.setNameStation(price.getStation().getName());
            dto.setLocationStation(price.getStation().getLocation());
            dto.setZoneStation(price.getStation().getZone());
            dto.setIdStation(price.getStation().getId());
        }
        
        dto.setPrice(price.getPrice());
        dto.setNameVehicleType(price.getVehicletype().getName());
        dto.setFromDate(price.getFromDate());
        
        
        return dto;
    }
}
