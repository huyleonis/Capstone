package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Services.PriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceServiceImpl priceServiceImpl;


    @RequestMapping(value = "findPriceDriver/{uuid}/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PriceDTO findPriceByUuidAndUsername(@PathVariable String uuid, @PathVariable String username){
        return priceServiceImpl.findPriceByUuidAndUsername(uuid, username);
    }

    @RequestMapping(value = "findPriceStaff/{stationId}/{licensePlate}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PriceDTO findPriceByStationIdAndLicensePlate(@PathVariable int stationId, @PathVariable String licensePlate){
        return priceServiceImpl.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
    }
}
