package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    static final int ACTIVE = 5;

    /**
     * Hiển thị trang lane.jsp
     *
     * @return lane view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewLane() {
        ModelAndView m = new ModelAndView("lane");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Lane Management");
        return m;
    }

    /**
     * Lấy danh sách lane
     *
     * @return Danh sách Lane dưới dạng JSONARRAY
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllLane() throws JsonProcessingException {
        List<LaneDTO> dtos = laneService.getAlllane();
        return new Gson().toJson(dtos);
    }

    /**
     * Tạo lane mới
     *
     * @param lane Lane entity
     * @return kểt quả thực hiện
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Lane lane) {
        boolean isSuccessful = false;
        LaneDTO dto = laneService.insert(lane);
        if (dto != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     *
     * @param lane station entity
     * @return kểt quả thực hiện
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Lane lane) {
        boolean isSuccessful = false;
        LaneDTO dto = laneService.update(lane);
        if (dto != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * active lane
     * @param lane
     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public String active(@RequestBody Lane lane) {

        boolean isSuccessful = laneService.active(lane);

        return (isSuccessful)? "success" : "fail";
    }

    /**
     * deactive lane
     * @param lane
     * @return
     */
    @RequestMapping(value = "/deactive", method = RequestMethod.POST)
    public String deactive(@RequestBody Lane lane) {

        boolean isSuccessful = laneService.deactive(lane);

        return (isSuccessful)? "success" : "fail";
    }
}
