package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Vehicletype;

public class VehicletypeDTO {

	private int id;
	private String name;

	public VehicletypeDTO() {
	}

	public VehicletypeDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static VehicletypeDTO convertFromEntity(Vehicletype vehicletype) {
		
		VehicletypeDTO dto = new VehicletypeDTO();
		
		dto.setId(vehicletype.getId());
		dto.setName(vehicletype.getName());
		
		return dto;
	}
}
