package hackathon.fpt.ktk.dto;

import hackathon.fpt.ktk.entity.Lane;

public class LaneDTO {

    private int id;
    private String name;
    private int stationId;
    private boolean active;

    public LaneDTO() {
    }

    public LaneDTO(int id, String name, int stationId, boolean active) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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
