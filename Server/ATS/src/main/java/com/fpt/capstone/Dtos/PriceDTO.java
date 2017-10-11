package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Price;

import java.util.Date;

public class PriceDTO {

    private int id;
    private int stationId;
    private double price;
    private int typeId;
    private Date fromDate;

    public PriceDTO() {
    }

    public PriceDTO(int id, int stationId, double price, int typeId, Date fromDate) {
        this.id = id;
        this.stationId = stationId;
        this.price = price;
        this.typeId = typeId;
        this.fromDate = fromDate;
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

    public static PriceDTO convertFromEntity(Price price){
        PriceDTO dto = new PriceDTO();

        dto.setId(price.getId());
        dto.setFromDate(price.getFromDate());
        dto.setPrice(price.getPrice());
        dto.setStationId(price.getStation().getId());
        dto.setTypeId(price.getVehicletype().getId());

        return dto;
    }
}
