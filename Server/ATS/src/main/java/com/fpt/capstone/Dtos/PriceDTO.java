package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Entities.Station;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PriceDTO {

    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "Inactive";

    private int id;
    private String stationName;
    private String stationLocation;
    private String stationZone;
    private double price;
    private String fromDate;
    private int stationId;
    private String vehicleType;
    private int vehicleTypeId;
    private String active;

    public PriceDTO() {
    }

    public PriceDTO(int id, String stationName, String stationLocation, String stationZone, double price,
                    String fromDate, int stationId, String vehicleType, int vehicleTypeId, String active) {
        this.id = id;
        this.stationName = stationName;
        this.stationLocation = stationLocation;
        this.stationZone = stationZone;
        this.price = price;
        this.fromDate = fromDate;
        this.stationId = stationId;
        this.vehicleType = vehicleType;
        this.vehicleTypeId = vehicleTypeId;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationLocation() {
        return stationLocation;
    }

    public void setStationLocation(String stationLocation) {
        this.stationLocation = stationLocation;
    }

    public String getStationZone() {
        return stationZone;
    }

    public void setStationZone(String stationZone) {
        this.stationZone = stationZone;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public static PriceDTO convertFromEntity(Price price) {
        PriceDTO dto = new PriceDTO();

        if (price == null) {
            dto = null;
        } else {
            dto.setId(price.getId());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dto.setFromDate(sdf.format(price.getFromDate()));

            if (price.getPrice() <= 0) {
                dto.setPrice(0);
            } else {
                dto.setPrice(price.getPrice());
            }

            if (price.getStation() != null) {
                dto.setStationId(price.getStation().getId());
                dto.setStationName(price.getStation().getName());
                dto.setStationZone(price.getStation().getZone());
            }

            if (price.getVehicleType() != null) {
                dto.setVehicleTypeId(price.getVehicleType().getId());
                dto.setVehicleType(price.getVehicleType().getName());
            }

            dto.setActive((price.getActive()) ? ACTIVE : INACTIVE);
        }
        return dto;
    }
}
