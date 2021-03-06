package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.VehicleDTO;
import com.fpt.capstone.Entities.Vehicle;
import com.fpt.capstone.Repositories.VehicleRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Dtos.VehicleDTO;
import com.fpt.capstone.Entities.Vehicle;
import com.fpt.capstone.Repositories.VehicleRepos;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepos vehicleRepos;

    @Override
    public int getVehicleId(String licensePlate) {
        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);
        
        if (vehicle == null) {
            return -1;
        }
        
        return vehicle.getId();
    }


    @Override
    public VehicleDTO getVehicleByLicensePlate(String licensePlate) {

        VehicleDTO dto = null;

        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);
        if (vehicle != null) {
            dto = VehicleDTO.convertFromEntity(vehicle);
        } else {
            return null;
        }

        return dto;
    }
}