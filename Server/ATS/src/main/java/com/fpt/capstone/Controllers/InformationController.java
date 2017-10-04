package com.fpt.capstone.Controllers;


import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Dtos.VehicleDTO;
import com.fpt.capstone.Services.StationServiceImpl;
import com.fpt.capstone.Services.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ats")
public class InformationController {

//    @RequestMapping("/")
//    @ResponseBody
//    public String viewInformaiton(){
//        return "demo";
//    }

    @Autowired
    private VehicleServiceImpl vehicleRepos;


//    @RequestMapping(value = "/{licensePlate}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public Vehicle getVehicle(@PathVariable("licensePlate") String licensePlate){
//        return vehicleRepos.find(licensePlate);
//    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDTO> getall()
    {
        return vehicleRepos.getall();
    }


}
