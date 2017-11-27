/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Chi Hieu
 */
@Controller
public class HomeController {

    static final int ACTIVE = 1;
    /**
     * Hiển thị trang welcome.jsp (HOME)
     * 
     * @return welcome view
     */
    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView m = new ModelAndView("welcome");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Dashboard");
        return m;
    }
}
