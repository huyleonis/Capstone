package hackathon.fpt.ktk.dto;

import hackathon.fpt.ktk.entity.Transaction;

public class TransactionDetailDTO extends TransactionDTO {

    private String laneName;
    private String stationName;
    private String zone;
    private String location;
    private String username;
    private String licensePlate;
    private String vehicleType;

    public TransactionDetailDTO() {
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public static TransactionDetailDTO covertFromEntity(Transaction tran) {
        TransactionDetailDTO dto = new TransactionDetailDTO();

        TransactionDTO.convertFromEntity(tran, dto);

        if (tran.getLane() != null) {
            dto.setLaneName(tran.getLane().getName());
        }
        if (tran.getStation() != null) {
            dto.setStationName(tran.getStation().getName());
            dto.setZone(tran.getStation().getZone());
            dto.setLocation(tran.getStation().getLocation());
        }

        if (tran.getVehicle() != null) {
            dto.setLicensePlate(tran.getVehicle().getLicensePlate());
            dto.setVehicleType(tran.getVehicle().getVehicleType().getName());
            if (tran.getVehicle().getAccount() != null) {
                dto.setUsername(tran.getVehicle().getAccount().getUsername());
            }
        }

        return dto;
    }
}
