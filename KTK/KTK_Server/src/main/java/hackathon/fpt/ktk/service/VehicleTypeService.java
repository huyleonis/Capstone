package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.VehicleTypeDTO;
import hackathon.fpt.ktk.entity.VehicleType;

import java.util.List;

public interface VehicleTypeService {

    List<VehicleTypeDTO> getAllVehicleType();

    VehicleTypeDTO insert(VehicleType vehicletype);

    VehicleTypeDTO update(VehicleType vehicletype);

    boolean delete(int id);
}
