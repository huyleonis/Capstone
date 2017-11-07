package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Station;

public class StationDTO {

	private static final String ACTIVE = "Active";
	private static final String INACTIVE = "Inactive";

	private int id;
	private String name;
	private String location;
	private String zone;
	private String active;

	public StationDTO() {
	}

	public StationDTO(int id, String name, String location, String zone, String active) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.zone = zone;
		this.active = active;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static StationDTO convertFromEntity(Station station) {
		StationDTO dto = new StationDTO();

		dto.setId(station.getId());
		dto.setName(station.getName());
		dto.setLocation(station.getLocation());
		dto.setZone(station.getZone());
		dto.setActive((station.getActive() == 1) ? ACTIVE : INACTIVE);

		return dto;
	}
}
