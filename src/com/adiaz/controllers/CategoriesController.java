package com.adiaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.services.CategoriesManager;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

	@Autowired CategoriesManager categoriesManager;
	
	@RequestMapping("/list")
	public ModelAndView getCategoriesList() {
		ModelAndView modelAndView = new ModelAndView("categories_list");
		modelAndView.addObject("categories_list", categoriesManager.queryCategories());
		return modelAndView;		
	}
}
