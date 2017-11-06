package com.fpt.capstone.Dtos;

public class VehicleDTO {

	private int id;
	private String licensePlate;
	private int typeId;

	public VehicleDTO() {
		// TODO Auto-generated constructor stub
	}

	public VehicleDTO(int id, String licensePlate, int typeId) {
		super();
		this.id = id;
		this.licensePlate = licensePlate;
		this.typeId = typeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
