package com.adiaz.controllers;

import javax.servlet.http.HttpServletRequest;

import com.adiaz.services.TownManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.services.CategoriesManager;
import com.adiaz.services.SportsManager;

@Controller
@SessionAttributes ({"sports", "categories", "towns"})
public class MainController {

	@Autowired
	SportsManager sportsManager;
	@Autowired
	CategoriesManager categoriesManager;
	@Autowired
	TownManager townManager;
	/*
	@Autowired
	CompetitionsManager competitionsManager;
	@Autowired
	MatchesManager matchesManager;
	*/



	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("sports", sportsManager.querySports());
		modelAndView.addObject("categories", categoriesManager.queryCategories());
		modelAndView.addObject("towns", townManager.queryAll());
		return modelAndView;
	}
	
	@RequestMapping (value="/login", method=RequestMethod.GET)
	public String goLogin(){
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "login"; 
	}
	
	@RequestMapping (value="/loginfailed", method=RequestMethod.GET)
	public String goLoginFailed(ModelMap modelMap, HttpServletRequest request){
		Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Usuario o contraseña incorrectos";
		} else if (exception instanceof LockedException) {
			error = "Usuario bloquedao";
		} else {
			error = "Usuario o contraseña incorrectos";
		}
		modelMap.addAttribute("error", error);
		return "login";
	}
}