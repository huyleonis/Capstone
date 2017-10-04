package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Services.StationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class StationController {

    @Autowired
    private StationServiceImpl stationServiceImpl;

    @RequestMapping(value = "/station/{uuid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public StationDTO findByUuid(@PathVariable String uuid) {
        return stationServiceImpl.findByUuid(uuid);
    }
}
