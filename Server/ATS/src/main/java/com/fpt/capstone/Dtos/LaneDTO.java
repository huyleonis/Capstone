package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Lane;

public class LaneDTO {

	private int id;
	private String name;
	private int stationId;
	private int active;

	public LaneDTO() {
	}

	public LaneDTO(int id, String name, int stationId, int active) {
		super();
		this.id = id;
		this.name = name;
		this.stationId = stationId;
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

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public static LaneDTO convertFromEntity(Lane lane) {
		
		LaneDTO dto = new LaneDTO();
		
		dto.setId(lane.getId());
		dto.setName(lane.getName());
		
		if (lane.getStation() != null) {
			dto.setStationId(lane.getStation().getId());
		}
		dto.setActive(lane.getActive());
		
		return dto;
	}
}
