package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.LaneDTO;
import com.fpt.capstone.Entities.Lane;
import com.fpt.capstone.Services.LaneService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/lane")
public class LaneController {

	@Autowired
	private LaneService laneService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView viewAccount() {
		ModelAndView m = new ModelAndView("lane");
		return m;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getAllLane() throws JsonProcessingException {

		List<LaneDTO> dtos = laneService.getAlllane();

		return new Gson().toJson(dtos);
	}

	@RequestMapping(value = "/getByStation", method = RequestMethod.GET)
	public List<LaneDTO> getLanesByStation(@Param("stationId") String stationId) {

		List<LaneDTO> dtos = laneService.getLanesByStation(Integer.parseInt(stationId));

		return dtos;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestBody Lane lane) {

		boolean isSuccessful = false;

		LaneDTO dto = laneService.insert(lane);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestBody Lane lane) {
		boolean isSuccessful = false;

		LaneDTO dto = laneService.update(lane);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}
}
