package hackathon.fpt.ktk.dto;

import hackathon.fpt.ktk.entity.Station;

public class StationDTO {

    private int id;
    private String name;
    private String location;
    private String zone;
    private boolean active;

    public StationDTO() {
    }

    public StationDTO(int id, String name, String location, String zone, boolean active) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static StationDTO convertFromEntity(Station station) {
        StationDTO dto = new StationDTO();

        dto.setId(station.getId());
        dto.setName(station.getName());
        dto.setLocation(station.getLocation());
        dto.setZone(station.getZone());
        dto.setActive(station.getActive());

        return dto;
    }
}
