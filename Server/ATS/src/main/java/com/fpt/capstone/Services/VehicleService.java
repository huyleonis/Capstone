package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.VehicleDTO;

public interface VehicleService {
    int getVehicleId(String licensePlate);

    VehicleDTO getVehicleByLicensePlate(String licensePlate);
}