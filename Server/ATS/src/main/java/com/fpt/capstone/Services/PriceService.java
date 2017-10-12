package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.PriceDTO;

public interface PriceService {

    PriceDTO findPriceByStationAndUsername(int stationId, String username);

    PriceDTO findPriceByStationIdAndLicensePlate(int stationId, String licensePlate);
}
