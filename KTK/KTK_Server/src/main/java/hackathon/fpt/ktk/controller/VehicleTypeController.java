package hackathon.fpt.ktk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import hackathon.fpt.ktk.dto.VehicleTypeDTO;
import hackathon.fpt.ktk.entity.VehicleType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/vehicletype")
public class VehicleTypeController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewVehicleType() {
        ModelAndView m = new ModelAndView("vehicletype");
        return m;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllVehicleType() throws JsonProcessingException {

        List<VehicleTypeDTO> dtos = vehicleTypeService.getAllVehicleType();

        return new Gson().toJson(dtos);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody VehicleType vehicletype) {

        boolean isSuccessful = false;

        VehicleTypeDTO dto = vehicleTypeService.insert(vehicletype);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody VehicleType vehicletype) {
        boolean isSuccessful = false;

        VehicleTypeDTO dto = vehicleTypeService.update(vehicletype);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody VehicleType vehicletype) {
        boolean isSuccessful = false;

        isSuccessful = vehicleTypeService.delete(vehicletype.getId());

        return (isSuccessful) ? "success" : "fail";
    }
}
