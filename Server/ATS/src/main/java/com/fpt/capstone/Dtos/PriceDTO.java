package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Price;

import java.util.Date;

public class PriceDTO {

    private int id;
    private int stationId;
    private double price;
    private int typeId;
    private Date fromDate;
    private String typeVehicle;

    public PriceDTO() {
    }

    public PriceDTO(int id, int stationId, double price, int typeId, Date fromDate) {
        this.id = id;
        this.stationId = stationId;
        this.price = price;
        this.typeId = typeId;
        this.fromDate = fromDate;
    }

    public PriceDTO(int id, int stationId, double price, int typeId, Date fromDate, String typeVehicle) {
        this.id = id;
        this.stationId = stationId;
        this.price = price;
        this.typeId = typeId;
        this.fromDate = fromDate;
        this.typeVehicle = typeVehicle;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
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
            dto.setStationId(price.getStation().getId());
            dto.setTypeId(price.getVehicletype().getId());
            if ("".equals(price.getVehicletype().getName())) {
                dto.setTypeVehicle(null);
            } else {
                dto.setTypeVehicle(price.getVehicletype().getName());
            }
        }
        return dto;
    }
}
