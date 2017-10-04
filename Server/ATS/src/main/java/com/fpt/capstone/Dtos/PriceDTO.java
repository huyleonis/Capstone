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
    private Date from_date;

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

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public static PriceDTO convertFromEntity(Price price){
        PriceDTO dto = new PriceDTO();

        int id = price.getId();
        String nameStation = price.getStation().getName();
        String locationStation = price.getStation().getLocation();
        String zoneStation = price.getStation().getZone();
        double price1 = price.getPrice();
        String nameVehicleType = price.getVehicletype().getName();
        Date from_date = price.getFromDate();

        dto.setId(id);
        dto.setNameStation(nameStation);
        dto.setLocationStation(locationStation);
        dto.setZoneStation(zoneStation);
        dto.setPrice(price1);
        dto.setNameVehicleType(nameVehicleType);
        dto.setFrom_date(from_date);

        return dto;
    }
}
