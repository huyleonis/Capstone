package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Dtos.VehicletypeDTO;
import com.fpt.capstone.Entities.Station;
import com.fpt.capstone.Entities.Vehicletype;
import com.fpt.capstone.Services.VehicletypeService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/vehicletype")
public class VehicletypeController {
	
	@Autowired
	private VehicletypeService vehicletypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView viewAccount() {
		ModelAndView m = new ModelAndView("vehicletype");
		return m;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getAllVehicleType() throws JsonProcessingException {

		List<VehicletypeDTO> dtos = vehicletypeService.getAllVehicleType();
		
		return new Gson().toJson(dtos);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestBody Vehicletype vehicletype) {

		boolean isSuccessful = false;

		VehicletypeDTO dto = vehicletypeService.insert(vehicletype);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestBody Vehicletype vehicletype) {
		boolean isSuccessful = false;

		VehicletypeDTO dto = vehicletypeService.update(vehicletype);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody Vehicletype vehicletype) {
		boolean isSuccessful = false;

		isSuccessful = vehicletypeService.delete(vehicletype.getId());

		return (isSuccessful) ? "success" : "fail";
	}
}
