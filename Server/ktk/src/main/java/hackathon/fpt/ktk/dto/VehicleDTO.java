package hackathon.fpt.ktk.dto;

import hackathon.fpt.ktk.entity.Vehicle;

public class VehicleDTO {

    private int id;
    private String licensePlate;
    private int typeId;

    public VehicleDTO() {
    }

    public VehicleDTO(int id, String licensePlate, int typeId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public static VehicleDTO convertFromEntity(Vehicle vehicle){
        VehicleDTO dto = new VehicleDTO();

        dto.setId(vehicle.getId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setTypeId(vehicle.getVehicleType().getId());

        return dto;
    }
}
