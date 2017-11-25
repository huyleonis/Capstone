package hackathon.fpt.ktk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import hackathon.fpt.ktk.dto.PriceDTO;
import hackathon.fpt.ktk.entity.Price;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/price")
public class PriceController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewPrice() {
        ModelAndView m = new ModelAndView("price");
        return m;
    }

    @RequestMapping(value = "findPriceStaff/{stationId}/{licensePlate}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PriceDTO getPriceByStaff(@PathVariable int stationId,
                                    @PathVariable String licensePlate) {
        return priceService.getPriceByStationIdAndLicensePlate(stationId, licensePlate);
    }

    @RequestMapping(value = "findByLicensePlate/{licensePlate}/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PriceDTO findByLicensePlate(@PathVariable String licensePlate,
                                       @PathVariable int id) {
        return priceService.findByLicensePlate(licensePlate, id);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllPrice() throws JsonProcessingException {

        List<PriceDTO> dtos = priceService.getAllPrice();

        return new Gson().toJson(dtos);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Price price) {

        boolean isSuccessful = false;

        PriceDTO dto = priceService.insert(price);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Price price) {
        boolean isSuccessful = false;

        PriceDTO dto = priceService.update(price);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }
}
