/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon.fpt.ktk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Chi Hieu
 */
@RestController
@RequestMapping(value = "/report")
public class ReportController extends AbstractController{

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewReport() {
        ModelAndView m = new ModelAndView("report");
        return m;
    }

    @RequestMapping(value = "/report_{transactionId}_{key}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewReport(@PathVariable String transactionId, @PathVariable String key) {
        ModelAndView m = new ModelAndView("report");
        m.addObject("transactionId", transactionId);
        m.addObject("key", key);
        return m;
    }
}
