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
    private int id;
    private String uuid;
    private int major;
    private int minor;
    private String type;
    private int laneId;
    private int stationId;

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

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
    
    public static BeaconDTO convertFromEntity(Beacon beacon) {
        BeaconDTO dto = new BeaconDTO();
        
        dto.setId(beacon.getId());
        dto.setUuid(beacon.getUuid());
        dto.setMajor(beacon.getMajor());
        dto.setMinor(beacon.getMinor());
        dto.setType(beacon.getType() == false? BEACON_PAYMENT : BEACON_RESULT );
        if (beacon.getLaneId().getId() != null) {
            dto.setLaneId(beacon.getLaneId().getId());
        }
        if (beacon.getStationId() != null) {
            dto.setStationId(beacon.getStationId().getId());
        }     
        return dto;
    }
    
}
