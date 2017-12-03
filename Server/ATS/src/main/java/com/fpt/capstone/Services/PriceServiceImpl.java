package com.fpt.capstone.Services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Repositories.PriceRepos;

@Service
public class PriceServiceImpl implements PriceService {

	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private PriceRepos priceRepos;

	@Override
	public PriceDTO findPriceByStationIdAndLicensePlate(int stationId, String licensePlate) {
		Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
		if (price != null) {
			PriceDTO priceDTO = PriceDTO.convertFromEntity(price);
			return priceDTO;
		} else {
			System.out.println("price is null");
			return null;
		}
	}

	@Override
	public PriceDTO findPriceById(int priceId) {
		PriceDTO dto = PriceDTO.convertFromEntity(priceRepos.findById(priceId));

		return dto;
	}

	@Override
	public List<PriceDTO> getAllPrice() {

		List<Price> prices = priceRepos.findAll();
		List<PriceDTO> dtos = new ArrayList<>();
		
		for (Price price : prices) {
			PriceDTO dto = PriceDTO.convertFromEntity(price);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public PriceDTO insert(Price price) {
		PriceDTO dto = null;

		try {
			Price processedPrice = priceRepos.save(price);
			if (processedPrice != null) {
				dto = PriceDTO.convertFromEntity(processedPrice);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dto;
	}

	@Override
	public PriceDTO update(Price price) {
		PriceDTO dto = null;

		try {
			Price existingPrice = priceRepos.findOne(price.getId());

			if (existingPrice != null) {
				Price processedPrice = priceRepos.save(price);
				dto = PriceDTO.convertFromEntity(processedPrice);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dto;
	}

	@Override
	public boolean active(Price price) {
		Price existingPrice = priceRepos.findOne(price.getId());
		if (existingPrice != null) {
			existingPrice.setActive(true);
			Price processedPrice = priceRepos.save(existingPrice);
			if (processedPrice != null) {
				System.out.println("Active Price: " + processedPrice.getActive());
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean deactive(Price price) {
		Price existingPrice = priceRepos.findOne(price.getId());
		if (existingPrice != null) {
			existingPrice.setActive(false);
			Price processedPrice = priceRepos.save(existingPrice);
			if (processedPrice != null) {
				System.out.println("Deactive Price: " + processedPrice.getActive());
				return true;
			}
		}

		return false;
	}
}
