package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.PriceDTO;
import hackathon.fpt.ktk.entity.Price;

import java.util.List;

public interface PriceService {

    PriceDTO getPriceByStationIdAndLicensePlate(int stationId, String licensePlate);

    PriceDTO findByLicensePlate(String licensePlate, int id);

    List<PriceDTO> getAllPrice();

    PriceDTO insert(Price price);

    PriceDTO update(Price price);
}
