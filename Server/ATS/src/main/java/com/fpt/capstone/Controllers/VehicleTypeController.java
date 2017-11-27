package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.VehicleTypesDTO;
import com.fpt.capstone.Entities.VehicleType;
import com.fpt.capstone.Services.VehicleTypeService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/vehicletype")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicletypeService;

    static final int ACTIVE = 7;

    /**
     * Hiển thị trang vehicletype.jsp
     *
     * @return vehicletype view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewVehicleType() {
        ModelAndView m = new ModelAndView("vehicletype");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Vehicle Type Management");
        return m;
    }

    /**
     * Lấy danh sách vehicle type
     *
     * @return Danh sách vehicel type dưới dạng JSONARRAY
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllVehicleType() throws JsonProcessingException {
        List<VehicleTypesDTO> dtos = vehicletypeService.getAllVehicleType();
        return new Gson().toJson(dtos);
    }

    /**
     * Tạo vehicle type mới
     *
     * @param vehicleType VehicleType entity
     * @return kểt quả thực hiện
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody VehicleType vehicleType) {
        boolean isSuccessful = false;
        VehicleTypesDTO dto = vehicletypeService.insert(vehicleType);
        if (dto != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Update vehicle type
     *
     * @param vehicleType VehicleType entity
     * @return kểt quả thực hiện
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody VehicleType vehicleType) {
        boolean isSuccessful = false;
        VehicleTypesDTO dto = vehicletypeService.update(vehicleType);
        if (dto != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }
}
