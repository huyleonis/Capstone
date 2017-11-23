package com.fpt.capstone.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/dump")
public class DumpController {
	
	@RequestMapping
	public ModelAndView viewDump() {
		ModelAndView m = new ModelAndView("dump");
		return m;
	}
	
}
