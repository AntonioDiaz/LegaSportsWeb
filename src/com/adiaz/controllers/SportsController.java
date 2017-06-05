//TODO DELETE??
package com.adiaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.services.SportCenterManager;

@Controller
@RequestMapping("/sports")
public class SportsController {

	@Autowired SportCenterManager sportsCenterManager;
	//private static final Logger logger = Logger.getLogger(SportsController.class);
	
	@RequestMapping("/list")
	public ModelAndView getSportsList() {
		ModelAndView modelAndView = new ModelAndView("sports_list");
		return modelAndView;
	}	
}
