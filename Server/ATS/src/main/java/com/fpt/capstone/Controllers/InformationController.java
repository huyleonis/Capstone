package com.fpt.capstone.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
