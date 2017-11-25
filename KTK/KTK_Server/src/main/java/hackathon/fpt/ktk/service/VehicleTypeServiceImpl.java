package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.VehicleTypeDTO;
import hackathon.fpt.ktk.entity.VehicleType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleTypeServiceImpl extends AbstractServiceImpl implements VehicleTypeService {

    @Override
    public List<VehicleTypeDTO> getAllVehicleType() {

        List<VehicleType> list = vehicleTypeRepos.findAll();
        List<VehicleTypeDTO> dtos = new ArrayList<>();

        for (VehicleType vehicletype : list) {
            VehicleTypeDTO dto = VehicleTypeDTO.convertFromEntity(vehicletype);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public VehicleTypeDTO insert(VehicleType vehicletype) {
        VehicleTypeDTO dto = null;

        try {
            VehicleType processedVehicleType = vehicleTypeRepos.save(vehicletype);
            if (processedVehicleType != null) {
                dto = VehicleTypeDTO.convertFromEntity(processedVehicleType);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public VehicleTypeDTO update(VehicleType vehicletype) {
        VehicleTypeDTO dto = null;

        try {
            VehicleType existingVehicleType = vehicleTypeRepos.findOne(vehicletype.getId());

            if (existingVehicleType != null) {
                VehicleType processedVehicleType = vehicleTypeRepos.save(vehicletype);
                dto = VehicleTypeDTO.convertFromEntity(processedVehicleType);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public boolean delete(int id) {

        try {
            VehicleType existingVehicleType = vehicleTypeRepos.findOne(id);

            if (existingVehicleType != null) {
                vehicleTypeRepos.delete(id);
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }
}
