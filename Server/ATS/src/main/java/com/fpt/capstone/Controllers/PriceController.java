package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Services.PriceService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/price")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView viewAccount() {
		ModelAndView m = new ModelAndView("price");
		return m;
	}

	@RequestMapping(value = "findPriceDriver/{idStation}/{username}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public PriceDTO findPriceForDriver(@PathVariable String idStation, @PathVariable String username) {
		System.out.println("Get Price from Driver");
		System.out.println("    + Station ID: " + idStation);
		System.out.println("    + username: " + username);
		int iIdStaion = Integer.parseInt(idStation);

		return priceService.findPriceByStationAndUsername(iIdStaion, username);
	}

	@RequestMapping(value = "findPriceStaff/{stationId}/{licensePlate}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public PriceDTO findPriceByStationIdAndLicensePlate(@PathVariable int stationId,
			@PathVariable String licensePlate) {
		return priceService.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
	}

	@RequestMapping(value = "findByLicensePlate/{license_plate}/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public PriceDTO findByLicensePlate(@PathVariable String license_plate, @PathVariable int id) {
		return priceService.findByLicensePlate(license_plate, id);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getAllPrice() throws JsonProcessingException {

		List<PriceDTO> dtos = priceService.getAllPrice();

		return new Gson().toJson(dtos);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestBody Price price) {

		boolean isSuccessful = false;
		
		PriceDTO dto = priceService.insert(price);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestBody Price price) {
		boolean isSuccessful = false;

		PriceDTO dto = priceService.update(price);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}
}
