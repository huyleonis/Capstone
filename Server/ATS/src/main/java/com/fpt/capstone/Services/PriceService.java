package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.PriceDTO;

public interface PriceService {

    PriceDTO findPriceByStationAndUsername(int stationId, String username);
    
    PriceDTO findByLicensePlate(String licensePlate, int id);    

    PriceDTO findPriceByStationIdAndLicensePlate(int stationId, String licensePlate);
    
    PriceDTO findPriceById(int idPrice);
}