package com.fpt.capstone.Entities;

import javax.persistence.*;

@Entity(name = "price")
public class PriceEntity {
    @Id
    @Column
    private Integer IdPrice;

    @ManyToOne
    @JoinColumn(name = "IdStation")
    private StationEntity Station;

    @Column
    private Long Price;

    @ManyToOne
    @JoinColumn(name = "IdType")
    private VehicleTypeEntity VehicleType;

    public Integer getIdPrice() {
        return IdPrice;
    }

    public void setIdPrice(Integer idPrice) {
        IdPrice = idPrice;
    }

    public StationEntity getStation() {
        return Station;
    }

    public void setStation(StationEntity station) {
        Station = station;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public VehicleTypeEntity getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(VehicleTypeEntity vehicleType) {
        VehicleType = vehicleType;
    }
}
