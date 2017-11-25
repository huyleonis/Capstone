package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.VehicleDTO;
import hackathon.fpt.ktk.entity.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl extends AbstractServiceImpl implements VehicleService {

    @Override
    public VehicleDTO getVehicleByLicensePlate(String licensePlate) {
        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);
        if (vehicle != null) {
            VehicleDTO dto = VehicleDTO.convertFromEntity(vehicle);
            return dto;
        }

        return null;
    }
}
