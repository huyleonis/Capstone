package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.PriceDTO;

public interface PriceService {

    PriceDTO findPriceByUuidAndUsername(String uuid, String username);

    PriceDTO findPriceByStationIdAndLicensePlate(int stationId, String licensePlate);
}
