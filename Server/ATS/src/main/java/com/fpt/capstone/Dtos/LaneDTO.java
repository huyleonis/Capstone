package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Lane;

public class LaneDTO {

	private static final String ACTIVE = "Active";
	private static final String INACTIVE = "Inactive";

	private int id;
	private String name;
	private int stationId;
	private String stationName;
	private String stationZone;
	private String active;

	public LaneDTO() {
	}

	public LaneDTO(int id, String name, int stationId, String stationName, String active, String stationZone) {
		this.id = id;
		this.name = name;
		this.stationId = stationId;
		this.stationName = stationName;
		this.stationZone = stationZone;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationZone() {
		return stationZone;
	}

	public void setStationZone(String stationZone) {
		this.stationZone = stationZone;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static LaneDTO convertFromEntity(Lane lane) {

		LaneDTO dto = new LaneDTO();

		dto.setId(lane.getId());
		dto.setName(lane.getName());

		if (lane.getStation() != null) {
			dto.setStationId(lane.getStation().getId());
			dto.setStationName(lane.getStation().getName());
			dto.setStationZone(lane.getStation().getZone());
		}

		dto.setActive((lane.getActive() == 1) ? ACTIVE : INACTIVE);

		return dto;
	}
}
