package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.VehicleDTO;

public interface VehicleService {

    VehicleDTO getVehicleByLicensePlate(String licensePlate);
}
