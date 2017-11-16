package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.VehicleTypesDTO;
import java.util.List;
import com.fpt.capstone.Entities.VehicleType;

public interface VehicleTypeService {

	List<VehicleTypesDTO> getAllVehicleType();

	VehicleTypesDTO insert(VehicleType vehicletype);

	VehicleTypesDTO update(VehicleType vehicletype);

	boolean delete(int id);
}
