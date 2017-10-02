package com.fpt.capstone.Entities;

import javax.persistence.*;

@Entity(name = "vehicle")
public class VehicleEntity {
    @Id
    @Column(name = "LicensePlate")
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "IdType")
    private VehicleTypeEntity vehicleType;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleTypeEntity getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeEntity vehicleType) {
        this.vehicleType = vehicleType;
    }
}
