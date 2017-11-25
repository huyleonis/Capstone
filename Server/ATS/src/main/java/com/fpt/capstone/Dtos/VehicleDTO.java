package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Vehicle;

public class VehicleDTO {

    private int id;
    private String licensePlate;
    private int vehicletype;

    public VehicleDTO() {
    }

    public VehicleDTO(int id, String licensePlate, int vehicletype) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.vehicletype = vehicletype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(int vehicletype) {
        this.vehicletype = vehicletype;
    }

    public static VehicleDTO convertFromEntity(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();

        dto.setId(vehicle.getId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setVehicletype(vehicle.getVehicleType().getId());

        return dto;
    }
}
