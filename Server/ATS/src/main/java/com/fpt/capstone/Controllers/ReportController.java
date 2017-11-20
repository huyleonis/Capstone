package com.fpt.capstone.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ReportController {

    @RequestMapping(value = "/report",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewReport() {
        ModelAndView m = new ModelAndView("report");
        return m;
    }

}
