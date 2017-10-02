package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.VehicleDTO;
import com.fpt.capstone.Entities.Vehicle;

import java.util.List;

public interface VehicleService {
    List<VehicleDTO> getall();
}
