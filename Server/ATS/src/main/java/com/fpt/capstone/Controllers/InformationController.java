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


    
    @RequestMapping(value = "/checkConnection", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String checkConnection() {
        System.out.println("Check Connection successfully.");
        return "true";
    }
    


}
