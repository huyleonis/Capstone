package com.fpt.capstone.Services;

import java.util.List;

import com.fpt.capstone.Dtos.VehicletypeDTO;
import com.fpt.capstone.Entities.Vehicletype;

public interface VehicletypeService {

	List<VehicletypeDTO> getAllVehicleType();

	VehicletypeDTO insert(Vehicletype vehicletype);

	VehicletypeDTO update(Vehicletype vehicletype);

	boolean delete(int id);
}
