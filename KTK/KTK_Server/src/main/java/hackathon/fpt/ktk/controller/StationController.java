package hackathon.fpt.ktk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import hackathon.fpt.ktk.dto.StationDTO;
import hackathon.fpt.ktk.entity.Station;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/station")
public class StationController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewStation() {
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
