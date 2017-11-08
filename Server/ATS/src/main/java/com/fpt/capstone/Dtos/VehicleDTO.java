package com.fpt.capstone.Dtos;

public class VehicleDTO {
    private String licensePlate;
    private int vehicletype;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(Integer vehicletype) {
        this.vehicletype = vehicletype;
    }

//    public static VehicleDTO convertFromEntity(Vehicle vehicle){
//        VehicleDTO dto = new VehicleDTO();
//
//        String licensePlate = vehicle.getLicensePlate();
//        Vehicletype vehicletype = vehicle.getVehicletype();
//
//        return dto;
//    }


}
