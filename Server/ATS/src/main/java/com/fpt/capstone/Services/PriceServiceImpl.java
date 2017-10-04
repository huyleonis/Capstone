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
    public PriceDTO findPriceByUuidAndUsername(String uuid, String username) {
        Price price = priceRepos.findPriceByUuidAndUsername(uuid, username);
        if (price != null){
            PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
            return priceDTO;
        } else {
            return null;
        }
    }
}
