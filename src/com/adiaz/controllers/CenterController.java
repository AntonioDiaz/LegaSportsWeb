package com.adiaz.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.entities.SportCenter;
import com.adiaz.forms.SportCenterFormValidator;
import com.adiaz.forms.SportCourtFormValidator;
import com.adiaz.forms.SportsCourtForm;
import com.adiaz.services.SportCenterManager;
import com.adiaz.services.SportCourtManager;

@Controller
@RequestMapping (value="/center")
public class CenterController {

	private static final Logger logger = Logger.getLogger(CenterController.class);
	
	@Autowired SportCenterManager sportsCenterManager;
	@Autowired SportCourtManager sportCourtManager;
	@Autowired SportCenterFormValidator sportCenterFormValidator;
	@Autowired SportCourtFormValidator sportCourtFormValidator;
	
	@RequestMapping("/list")
	public ModelAndView centerList(
			@RequestParam(value="update_done", defaultValue="false") boolean updateDone,
			@RequestParam(value="add_done", defaultValue="false") boolean addDone,
			@RequestParam(value="remove_done", defaultValue="false") boolean removeDone) {
		ModelAndView modelAndView = new ModelAndView("center_list");
		List<SportCenter> centers = sportsCenterManager.querySportCenters();
		modelAndView.addObject("centers", centers);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		return modelAndView;
	}
	
	@RequestMapping("/add")
	public ModelAndView addCenter() {
		ModelAndView modelAndView = new ModelAndView("center_add");
		modelAndView.addObject("my_form", new SportCenter());
		return modelAndView;
	}
	
	@RequestMapping("/doAdd")
	public ModelAndView doAddCenter(@ModelAttribute("my_form") SportCenter sportCenter, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCenterFormValidator.validate(sportCenter, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportCenter);
			modelAndView.setViewName("center_add");
		} else {
			try {
				sportsCenterManager.addSportCenter(sportCenter);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			String viewName = "redirect:/center/list";
			viewName += "?add_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}
	
	@RequestMapping("/update")
	public ModelAndView updateCenter(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView("center_update");
		SportCenter sportCenter = sportsCenterManager.querySportCentersById(id);
		modelAndView.addObject("my_form", sportCenter);
		return modelAndView;
	}
	
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdateCenter(@ModelAttribute("my_form") SportCenter sportCenter, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCenterFormValidator.validate(sportCenter, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportCenter);
			modelAndView.setViewName("center_update");
		} else {
			try {
				sportsCenterManager.updateSportCenter(sportCenter);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			String viewName = "redirect:/center/list";
			viewName += "?update_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public ModelAndView doDelete(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			sportsCenterManager.removeSportCenter(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		String viewName = "redirect:/center/list";
		viewName += "?remove_done=true";
		modelAndView.setViewName(viewName);
		return modelAndView;
	}
	
	@RequestMapping("/courtsList")
	public ModelAndView courtsList(@RequestParam Long id,
			@RequestParam(value="update_done", defaultValue="false") boolean updateDone,
			@RequestParam(value="add_done", defaultValue="false") boolean addDone,
			@RequestParam(value="remove_done", defaultValue="false") boolean removeDone) {
		ModelAndView modelAndView = new ModelAndView("courts_list");
		modelAndView.addObject("sportCenter", sportsCenterManager.querySportCentersById(id));
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);		
		return modelAndView;
	}
	
	@RequestMapping("/addCourt")
	public ModelAndView addCourt(@RequestParam Long idCenter) {
		ModelAndView modelAndView = new ModelAndView("courts_add");
		SportsCourtForm sportsCourtForm = new SportsCourtForm();
		sportsCourtForm.setIdCenter(idCenter);
		modelAndView.addObject("my_form", sportsCourtForm);
		SportCenter sportCenter = sportsCenterManager.querySportCentersById(idCenter);
		modelAndView.addObject("sportCenter", sportCenter);
		return modelAndView;
	}
	
	@RequestMapping("/doAddCourt")
	public ModelAndView doAddCourt(@ModelAttribute("my_form") SportsCourtForm sportsCourtForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCourtFormValidator.validate(sportsCourtForm, bindingResult);
		if (bindingResult.hasErrors()) {
			SportCenter sportCenter = sportsCenterManager.querySportCentersById(sportsCourtForm.getIdCenter());
			modelAndView.addObject("sportCenter", sportCenter);
			modelAndView.addObject("my_form", sportsCourtForm);
			modelAndView.setViewName("courts_add");
		} else {
			try {
				sportsCenterManager.addCourtToCenter(sportsCourtForm);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			String viewName = "redirect:/center/courtsList";
			viewName += "?add_done=true";
			viewName += "&id=" + sportsCourtForm.getIdCenter();
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}
}