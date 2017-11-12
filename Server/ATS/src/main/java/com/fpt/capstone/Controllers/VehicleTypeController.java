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
import com.fpt.capstone.Dtos.VehicleTypesDTO;
import com.fpt.capstone.Entities.Station;
import com.fpt.capstone.Entities.VehicleType;
import com.fpt.capstone.Services.VehicleTypeService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/vehicletype")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicletypeService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewAccount() {
        ModelAndView m = new ModelAndView("vehicletype");
        return m;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllVehicleType() throws JsonProcessingException {

        List<VehicleTypesDTO> dtos = vehicletypeService.getAllVehicleType();

        return new Gson().toJson(dtos);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody VehicleType vehicletype) {

        boolean isSuccessful = false;

        VehicleTypesDTO dto = vehicletypeService.insert(vehicletype);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody VehicleType vehicletype) {
        boolean isSuccessful = false;

        VehicleTypesDTO dto = vehicletypeService.update(vehicletype);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody VehicleType vehicletype) {
        boolean isSuccessful = false;

        isSuccessful = vehicletypeService.delete(vehicletype.getId());

        return (isSuccessful) ? "success" : "fail";
    }
}
