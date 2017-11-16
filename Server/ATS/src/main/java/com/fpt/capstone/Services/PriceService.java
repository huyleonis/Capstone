package com.fpt.capstone.Services;

import java.util.List;

import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Price;

public interface PriceService {    
    
    PriceDTO findByLicensePlate(String licensePlate, int id);    

    PriceDTO findPriceByStationIdAndLicensePlate(int stationId, String licensePlate);
    
    PriceDTO findPriceById(int idPrice);
    
    List<PriceDTO> getAllPrice();
    
    PriceDTO insert(Price price);
    
    PriceDTO update(Price price);
}