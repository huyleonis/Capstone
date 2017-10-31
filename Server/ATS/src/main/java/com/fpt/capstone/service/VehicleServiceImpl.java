package com.fpt.capstone.service;

import com.fpt.capstone.dto.VehicleDTO;
import com.fpt.capstone.repository.VehicleRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{
    @Autowired
    private VehicleRepos vehicleRepos;

    @Override
    public List<VehicleDTO> getall(){
//        List<Vehicle> listVehicle = vehicleRepos.getall();
//        List<VehicleDTO> listDTO = new ArrayList<>();
//        for(Vehicle vehicle : listVehicle){
//            listDTO.add(VehicleDTO.convertFromEntity(vehicle));
//        }
//        return listDTO;
        return null;
    }
}
