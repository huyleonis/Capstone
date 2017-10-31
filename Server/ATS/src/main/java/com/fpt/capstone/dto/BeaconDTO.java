package com.fpt.capstone.dto;

import com.fpt.capstone.entity.Beacon;

public class BeaconDTO {
    private int id;
    private String uuid;
    private int major;
    private int minor;
    private int stationId;
    private int laneId;
    private int type;

    public BeaconDTO() {
    }

    public BeaconDTO(int id, String uuid, int major, int minor, int stationId, int laneId, int type) {
        this.id = id;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.stationId = stationId;
        this.laneId = laneId;
        this.type = type;
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

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static BeaconDTO convertFromEntity(Beacon beacon) {
        BeaconDTO dto = new BeaconDTO();

        if (beacon != null) {
            if (beacon.getId() > 0) {
                dto.setId(beacon.getId());
            }
            if (beacon.getUuid() != null) {
                dto.setUuid(beacon.getUuid());
            }
            if (beacon.getMajor() > 0) {
                dto.setMajor(beacon.getMajor());
            }
            if (beacon.getMinor() > 0) {
                dto.setMinor(beacon.getMinor());
            }
            if (beacon.getStation() != null) {
                if (beacon.getStation().getId() > 0) {
                    dto.setStationId(beacon.getStation().getId());
                }
            }
            if (beacon.getLane() != null) {
                if (beacon.getLane().getId() != null) {
                    dto.setLaneId(beacon.getLane().getId());
                }
            }
            if (beacon.getType() > 0) {
                dto.setType(beacon.getType());
            }
        }

        return dto;
    }
}
