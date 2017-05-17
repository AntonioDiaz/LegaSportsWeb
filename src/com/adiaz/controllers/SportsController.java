package com.adiaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.services.CategoriesManager;
import com.adiaz.services.SportsManager;

@Controller
@RequestMapping("/sports")
public class SportsController {

	@Autowired SportsManager sportsManager;
	@Autowired CategoriesManager categoriesManager;
	
	@RequestMapping("/list")
	public ModelAndView getSportsList() {
		ModelAndView modelAndView = new ModelAndView("sports_list");
		modelAndView.addObject("sports_list", sportsManager.querySports());
		return modelAndView;
	}	
}
