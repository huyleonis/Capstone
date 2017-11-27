package com.fpt.capstone.Controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ats")
public class InformationController {

    @RequestMapping(value = "/checkConnection", method = RequestMethod.GET)
    @ResponseBody
    public String checkConnection() {
        System.out.println("Check Connection successfully.");
        return "true";
    }

}
