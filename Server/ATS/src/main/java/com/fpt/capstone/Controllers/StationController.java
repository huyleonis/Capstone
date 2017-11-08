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
import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Entities.Station;
import com.fpt.capstone.Services.StationService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/station")
public class StationController {

	@Autowired
	private StationService stationService;

	@RequestMapping(value = "/get/{uuid}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public StationDTO findByUuid(@PathVariable String uuid) {
		return stationService.findByUuid(uuid);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView viewAccount() {
		ModelAndView m = new ModelAndView("station");
		return m;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getAllLane() throws JsonProcessingException {

		List<StationDTO> dtos = stationService.getAllStation();

		return new Gson().toJson(dtos);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestBody Station station) {

		boolean isSuccessful = false;

		StationDTO dto = stationService.insert(station);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestBody Station station) {
		boolean isSuccessful = false;

		StationDTO dto = stationService.update(station);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}
}
