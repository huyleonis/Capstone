package hackathon.fpt.ktk.dto;

import hackathon.fpt.ktk.entity.VehicleType;

public class VehicleTypeDTO {

    private int id;
    private String name;

    public VehicleTypeDTO() {
    }

    public VehicleTypeDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static VehicleTypeDTO convertFromEntity(VehicleType vehicletype) {

        VehicleTypeDTO dto = new VehicleTypeDTO();

        dto.setId(vehicletype.getId());
        dto.setName(vehicletype.getName());

        return dto;
    }
}
