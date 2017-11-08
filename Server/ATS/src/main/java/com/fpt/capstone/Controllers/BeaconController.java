/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.fpt.capstone.Dtos.BeaconDTO;
import com.fpt.capstone.Entities.Beacon;
import com.fpt.capstone.Services.BeaconService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/beacon")
public class BeaconController {

	@Autowired
	private BeaconService beaconService;

	// @RequestMapping(value = "get/{uuid}/{major}/{minor}")
	// @ResponseStatus(HttpStatus.OK)
	// public BeaconDTO getBeacon(@PathVariable String uuid,
	// @PathVariable String major, @PathVariable String minor) {
	//
	// System.out.println("Get Beacon information: " + uuid + " - " + major + " - "
	// + minor);
	// int iMajor = Integer.parseInt(major);
	// int iMinor = Integer.parseInt(minor);
	//
	// Beacon beaconEntity = beaconRepos.getBeacon(uuid, iMajor, iMinor);
	// if (beaconEntity != null) {
	// BeaconDTO dto = BeaconDTO.convertFromEntity(beaconEntity);
	// System.out.println("Get beacon info - type " + dto.getType());
	// return dto;
	// } else {
	// BeaconDTO dto = new BeaconDTO();
	// dto.setId(0);
	// dto.setUuid(uuid);
	// dto.setMajor(iMajor);
	// dto.setMinor(iMinor);
	// dto.setType("other");
	// return dto;
	// }
	// }

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView viewAccount() {
		ModelAndView m = new ModelAndView("beacon");
		return m;
	}

	@RequestMapping(value = "/get/{uuid}", method = RequestMethod.GET)
	public BeaconDTO getBeacon(@PathVariable String uuid) {

		return null;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getAllBeacon() throws JsonProcessingException {

		List<BeaconDTO> dtos = beaconService.getAllBeacon();

		return new Gson().toJson(dtos);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestBody Beacon beacon) {

		boolean isSuccessful = false;
		
		BeaconDTO dto = beaconService.insert(beacon);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestBody Beacon beacon) {
		boolean isSuccessful = false;

		BeaconDTO dto = beaconService.update(beacon);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}
}
