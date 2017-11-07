/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Beacon;

/**
 *
 * @author hp
 */
public class BeaconDTO {

	private static final String BEACON_PAYMENT = "BEACON_PAYMENT";
	private static final String BEACON_RESULT = "BEACON_RESULT";
	private static final String ACTIVE = "Active";
	private static final String INACTIVE = "Inactive";

	private int id;
	private String uuid;
	private int major;
	private int minor;
	private String type;
	private int laneId;
	private String laneName;
	private int stationId;
	private String stationName;
	private String active;

	public BeaconDTO() {
	}

	public BeaconDTO(int id, String uuid, int major, int minor, String type, int laneId, String laneName, int stationId,
			String stationName, String active) {
		this.id = id;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.type = type;
		this.laneId = laneId;
		this.laneName = laneName;
		this.stationId = stationId;
		this.stationName = stationName;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLaneId() {
		return laneId;
	}

	public void setLaneId(int laneId) {
		this.laneId = laneId;
	}

	public String getLaneName() {
		return laneName;
	}

	public void setLaneName(String laneName) {
		this.laneName = laneName;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static BeaconDTO convertFromEntity(Beacon beacon) {
		BeaconDTO dto = new BeaconDTO();

		dto.setId(beacon.getId());
		dto.setUuid(beacon.getUuid());
		dto.setMajor(beacon.getMajor());
		dto.setMinor(beacon.getMinor());
		dto.setType((beacon.getType() == 0) ? BEACON_PAYMENT : BEACON_RESULT);

		if (beacon.getLane() != null) {
			dto.setLaneId(beacon.getLane().getId());
			dto.setLaneName(beacon.getLane().getName());
		} else {
			dto.setLaneId(0);
			dto.setLaneName("");
		}
		if (beacon.getStation() != null) {
			dto.setStationId(beacon.getStation().getId());
			dto.setStationName(beacon.getStation().getName());
		} else {
			dto.setStationId(0);
			dto.setStationName("");
		}

		dto.setActive((beacon.getActive() == 1) ? ACTIVE : INACTIVE);

		return dto;
	}

}
