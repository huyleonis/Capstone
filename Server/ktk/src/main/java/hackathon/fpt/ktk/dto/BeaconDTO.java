package hackathon.fpt.ktk.dto;

import hackathon.fpt.ktk.entity.Beacon;
import hackathon.fpt.ktk.util.BeaconType;

public class BeaconDTO {

    private int id;
    private String uuid;
    private int major;
    private int minor;
    private BeaconType type;
    private int laneId;
    private int stationId;
    private boolean active;

    public BeaconDTO() {
    }

    public BeaconDTO(int id, String uuid, int major, int minor, BeaconType type, int laneId, int stationId, boolean active) {
        this.id = id;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.type = type;
        this.laneId = laneId;
        this.stationId = stationId;
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

    public BeaconType getType() {
        return type;
    }

    public void setType(BeaconType type) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static BeaconDTO convertFromEntity(Beacon beacon) {
        BeaconDTO dto = new BeaconDTO();

        dto.setId(beacon.getId());
        dto.setUuid(beacon.getUuid());
        dto.setMajor(beacon.getMajor());
        dto.setMinor(beacon.getMinor());
        switch(beacon.getType()) {
            case 1:
                dto.setType(BeaconType.BEACON_PAYMENT);
                break;
            case 2:
                dto.setType(BeaconType.BEACON_RESULT);
                break;
            default:
                dto.setType(BeaconType.BEACON_OTHER);
                break;
        }

        if (beacon.getLane() != null) {
            dto.setLaneId(beacon.getLane().getId());
        }
        if (beacon.getStation() != null) {
            dto.setStationId(beacon.getStation().getId());
        }
        dto.setActive(beacon.getActive());

        return dto;
    }
}
