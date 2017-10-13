package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Services.PriceServiceImpl;
import java.util.Map;
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
        System.out.println("Get Price from Driver");
        System.out.println("    + UUID: " + uuid);
        System.out.println("    + username: " + username);
        
        return priceServiceImpl.findPriceByUuidAndUsername(uuid, username);
    }

    @RequestMapping(value = "findPriceStaff/{stationId}/{licensePlate}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PriceDTO findPriceByStationIdAndLicensePlate(@PathVariable int stationId, @PathVariable String licensePlate){
        return priceServiceImpl.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
    }
    
//    @RequestMapping(value = "findByLicensePlate/{license_plate}/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public PriceDTO findByLicensePlate(@PathVariable String license_plate, @PathVariable int id){
//        return priceServiceImpl.findByLicensePlate(license_plate, id);
//    }
    
        
    @RequestMapping(value = "findByLicensePlate/{license_plate}/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> findByLicensePlate(@PathVariable String license_plate, @PathVariable int id){
        
        
        return null;
    }
}
