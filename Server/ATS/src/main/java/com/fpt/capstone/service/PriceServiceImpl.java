package com.fpt.capstone.service;

import com.fpt.capstone.dto.PriceDTO;
import com.fpt.capstone.entity.Price;
import com.fpt.capstone.repository.PriceRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceServiceImpl extends AbstractServiceImpl implements PriceService {

    @Override
    public PriceDTO getPriceByUuidAndUsername(String uuid, String username) {
        Price price = priceRepos.findPriceByUuidAndUsername(uuid, username);
        if (price != null) {
            PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
            return priceDTO;
        } else {
            return null;
        }
    }

    @Override
    public PriceDTO getPriceByStationIdAndLicensePlate(int stationId, String licensePlate) {
        Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
        if (price != null) {
            PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
            return priceDTO;
        } else {
            return null;
        }
    }

    @Override
    public PriceDTO getPriceById(int priceId) {

        Price price = priceRepos.findOne(priceId);

        if (price != null) {
            PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
            return priceDTO;
        }

        return null;
    }

    @Override
    public PriceDTO insert(Price price) {
        PriceDTO priceDTO = null;

        Price processedPrice = priceRepos.save(price);

        if (processedPrice != null) {
            priceDTO = PriceDTO.convertFromEntity(processedPrice);
        }

        return priceDTO;
    }

    @Override
    public PriceDTO update(Price price) {

        PriceDTO priceDTO = null;

        Price existingPrice = priceRepos.findOne(price.getId());

        if (existingPrice != null) {
            Price processedPrice = priceRepos.save(price);
            priceDTO = PriceDTO.convertFromEntity(processedPrice);
        }

        return priceDTO;
    }

    @Override
    public List<PriceDTO> getAllPrice() {

        List<Price> prices = priceRepos.findAll();
        List<PriceDTO> dtos = new ArrayList<>();

        for (Price price : prices) {
            dtos.add(PriceDTO.convertFromEntity(price));
        }

        return dtos;
    }
}
