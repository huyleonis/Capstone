package com.fpt.capstone.Services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Dtos.VehicletypeDTO;
import com.fpt.capstone.Entities.Vehicletype;
import com.fpt.capstone.Repositories.VehicletypeRepos;

@Service
public class VehicletypeServiceImpl implements VehicletypeService {

	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private VehicletypeRepos vehicletypeRepos;

	@Override
	public List<VehicletypeDTO> getAllVehicleType() {

		List<Vehicletype> list = vehicletypeRepos.findAll();
		List<VehicletypeDTO> dtos = new ArrayList<>();

		for (Vehicletype vehicletype : list) {
			VehicletypeDTO dto = VehicletypeDTO.convertFromEntity(vehicletype);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public VehicletypeDTO insert(Vehicletype vehicletype) {
		VehicletypeDTO dto = null;

		try {
			Vehicletype processedVehicleType = vehicletypeRepos.save(vehicletype);
			if (processedVehicleType != null) {
				dto = VehicletypeDTO.convertFromEntity(processedVehicleType);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dto;
	}

	@Override
	public VehicletypeDTO update(Vehicletype vehicletype) {
		VehicletypeDTO dto = null;

		try {
			Vehicletype existingVehicleType = vehicletypeRepos.findOne(vehicletype.getId());

			if (existingVehicleType != null) {
				Vehicletype processedVehicleType = vehicletypeRepos.save(vehicletype);
				dto = VehicletypeDTO.convertFromEntity(processedVehicleType);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dto;
	}

	@Override
	public boolean delete(int id) {

		try {
			Vehicletype existingVehicleType = vehicletypeRepos.findOne(id);

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
