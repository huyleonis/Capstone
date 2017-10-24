package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Price;

import java.util.Date;

public class PriceDTO {

    private int id;
    private String nameStation;
    private String locationStation;
    private String zoneStation;
    private double price;    
    private Date fromDate;
    private int stationId;    
    private String typeVehicle;

    public PriceDTO() {
    }        

    public PriceDTO(int id, String nameStation, String locationStation, String zoneStation, double price, String nameVehicleType, Date fromDate, int stationId, String typeVehicle) {
        this.id = id;
        this.nameStation = nameStation;
        this.locationStation = locationStation;
        this.zoneStation = zoneStation;
        this.price = price;        
        this.fromDate = fromDate;
        this.stationId = stationId;
        this.typeVehicle = typeVehicle;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }    

    public void setNameStation(String nameStation) {
        this.nameStation = nameStation;
    }

    public String getNameStation() {
        return nameStation;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    
    public static PriceDTO convertFromEntity(Price price) {
        PriceDTO dto = new PriceDTO();
        if (price == null) {
            dto = null;
        } else {
            dto.setId(price.getId());
            dto.setFromDate(price.getFromDate());
            if (price.getPrice() <= 0) {
                dto.setPrice(0);
            } else {
                dto.setPrice(price.getPrice());
            }
            
            if (price.getStation() != null) {
                dto.setStationId(price.getStation().getId());
                dto.setNameStation(price.getStation().getName());
                dto.setZoneStation(price.getStation().getZone());
            }
            
         
            if (price.getVehicletype() != null) {
                dto.setTypeVehicle(price.getVehicletype().getName());
            }            
        }
        return dto;
    }
}
