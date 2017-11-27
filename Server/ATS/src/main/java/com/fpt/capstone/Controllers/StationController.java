package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Entities.Station;
import com.fpt.capstone.Services.StationService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    static final int ACTIVE = 4;

    /**
     * Hiển thị trang station.jsp
     *
     * @return station view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewStaion() {
        ModelAndView m = new ModelAndView("station");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Station Management");
        return m;
    }

    /**
     * Lấy danh sách station
     *
     * @return Danh sách Station dưới dạng JSONARRAY
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllStation() throws JsonProcessingException {
        List<StationDTO> dtos = stationService.getAllStation();
        return new Gson().toJson(dtos);
    }

    /**
     * Tạo staion mới
     *
     * @param station station entity
     * @return kểt quả thực hiện
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Station station) {
        boolean isSuccessful = false;
        StationDTO dto = stationService.insert(station);
        if (dto != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Enable/Disable station
     *
     * @param station station entity
     * @return kểt quả thực hiện
     */
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
