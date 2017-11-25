package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.PriceDTO;
import hackathon.fpt.ktk.entity.Price;
import hackathon.fpt.ktk.entity.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl extends AbstractServiceImpl implements PriceService {

    @Override
    public PriceDTO getPriceByStationIdAndLicensePlate(int stationId, String licensePlate) {

        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);

        if (vehicle != null) {
            int typeid = vehicle.getVehicleType().getId();
            Price price = priceRepos.findByStationIdAndTypeId(stationId, typeid);

            if (price != null) {
                PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
                return priceDTO;
            }
        }

        return null;
    }
}
