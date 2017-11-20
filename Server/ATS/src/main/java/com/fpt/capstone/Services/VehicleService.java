package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.VehicleDTO;

public interface VehicleService {

    VehicleDTO getVehicleByLicensePlate(String licensePlate);
}
