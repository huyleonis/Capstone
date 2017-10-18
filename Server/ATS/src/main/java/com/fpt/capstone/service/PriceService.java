package com.fpt.capstone.service;

import com.fpt.capstone.dto.PriceDTO;
import com.fpt.capstone.entity.Price;

import java.util.List;

public interface PriceService {

    PriceDTO getPriceByUuidAndUsername(String uuid, String username);

    PriceDTO getPriceByStationIdAndLicensePlate(int stationId, String licensePlate);

    PriceDTO getPriceById(int priceId);

    PriceDTO insert(Price price);

    PriceDTO update(Price price);

    List<PriceDTO> getAllPrice();
}
