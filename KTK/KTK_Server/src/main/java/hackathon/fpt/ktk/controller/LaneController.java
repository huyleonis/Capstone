package hackathon.fpt.ktk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import hackathon.fpt.ktk.dto.LaneDTO;
import hackathon.fpt.ktk.entity.Lane;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/lane")
public class LaneController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewLane() {
        ModelAndView m = new ModelAndView("lane");
        return m;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllLane() throws JsonProcessingException{

        List<LaneDTO> dtos = laneService.getAlllane();

        return new Gson().toJson(dtos);
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
