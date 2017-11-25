package com.fpt.capstone.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/report_{transactionId}_{key}" ,method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewReport(@PathVariable String transactionId, @PathVariable String key) {
        ModelAndView m = new ModelAndView("report");
        m.addObject("transactionId", transactionId);
        m.addObject("key", key);
        return m;
    }
}
