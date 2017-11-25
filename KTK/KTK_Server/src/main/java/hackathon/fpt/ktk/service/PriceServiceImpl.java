package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.PriceDTO;
import hackathon.fpt.ktk.entity.Price;
import hackathon.fpt.ktk.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public PriceDTO findByLicensePlate(String licensePlate, int id) {
        Price price = priceRepos.findByLicensePlate(licensePlate, id);
        if (price != null) {
            PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
            return priceDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<PriceDTO> getAllPrice() {
        List<Price> prices = priceRepos.findAll();
        List<PriceDTO> dtos = new ArrayList<>();

        for (Price price : prices) {
            PriceDTO dto = PriceDTO.convertFromEntity(price);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public PriceDTO insert(Price price) {
        PriceDTO dto = null;

        try {
            Price processedPrice = priceRepos.save(price);
            if (processedPrice != null) {
                dto = PriceDTO.convertFromEntity(processedPrice);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public PriceDTO update(Price price) {
        PriceDTO dto = null;

        try {
            Price existingPrice = priceRepos.findOne(price.getId());

            if (existingPrice != null) {
                Price processedPrice = priceRepos.save(price);
                dto = PriceDTO.convertFromEntity(processedPrice);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }
}
