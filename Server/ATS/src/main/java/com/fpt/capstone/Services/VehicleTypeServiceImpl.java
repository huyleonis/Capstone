package com.fpt.capstone.Services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Dtos.VehicleTypesDTO;
import com.fpt.capstone.Entities.VehicleType;
import com.fpt.capstone.Repositories.VehicleTypeRepos;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private VehicleTypeRepos vehicletypeRepos;

    @Override
    public List<VehicleTypesDTO> getAllVehicleType() {

        List<VehicleType> list = vehicletypeRepos.findAll();
        List<VehicleTypesDTO> dtos = new ArrayList<>();

        for (VehicleType vehicletype : list) {
            VehicleTypesDTO dto = VehicleTypesDTO.convertFromEntity(vehicletype);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public VehicleTypesDTO insert(VehicleType vehicletype) {
        VehicleTypesDTO dto = null;

        try {
            VehicleType processedVehicleType = vehicletypeRepos.save(vehicletype);
            if (processedVehicleType != null) {
                dto = VehicleTypesDTO.convertFromEntity(processedVehicleType);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public VehicleTypesDTO update(VehicleType vehicletype) {
        VehicleTypesDTO dto = null;

        try {
            VehicleType existingVehicleType = vehicletypeRepos.findOne(vehicletype.getId());

            if (existingVehicleType != null) {
                VehicleType processedVehicleType = vehicletypeRepos.save(vehicletype);
                dto = VehicleTypesDTO.convertFromEntity(processedVehicleType);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public boolean delete(int id) {

        try {
            VehicleType existingVehicleType = vehicletypeRepos.findOne(id);

            if (existingVehicleType != null) {
                vehicletypeRepos.delete(id);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return false;
    }

}
