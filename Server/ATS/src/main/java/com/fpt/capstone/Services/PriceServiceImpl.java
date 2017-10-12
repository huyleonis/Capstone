package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Repositories.PriceRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService{

    @Autowired
    private PriceRepos priceRepos;


    @Override
    public PriceDTO findPriceByStationAndUsername(int stationId, String username) {
        Price price = priceRepos.findPriceByStationAndUsername(username, stationId);
        if (price != null){
            PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
            return priceDTO;
        } else {
            return null;
        }
    }

    @Override
    public PriceDTO findPriceByStationIdAndLicensePlate(int stationId, String licensePlate) {
        Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
        if (price != null){
            PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
            return priceDTO;
        } else {
            return null;
        }
    }
}
