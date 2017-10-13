package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.PriceDTO;

public interface PriceService {

    PriceDTO findByLicensePlate(String licensePlate, int id);

    PriceDTO findPriceByUuidAndUsername(String uuid, String username);

    PriceDTO findPriceByStationIdAndLicensePlate(int stationId, String licensePlate);

    PriceDTO findPriceById(int priceId);
}
