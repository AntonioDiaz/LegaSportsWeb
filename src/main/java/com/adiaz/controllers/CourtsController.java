package com.adiaz.controllers;

import com.adiaz.entities.SportCenterCourt;
import com.adiaz.forms.SportCenterForm;
import com.adiaz.forms.SportsCourtForm;
import com.adiaz.forms.validators.SportCourtFormValidator;
import com.adiaz.services.SportCenterCourtManager;
import com.adiaz.services.SportCenterManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by toni on 20/09/2017.
 */
@Controller
@RequestMapping(value="/courts")
public class CourtsController {

	private static final Logger logger = Logger.getLogger(CourtsController.class);
	@Autowired
	SportCenterCourtManager sportCenterCourtManager;
	@Autowired
	SportCourtFormValidator sportCourtFormValidator;
	@Autowired
	SportCenterManager sportsCenterManager;

	@RequestMapping("/list")
	public ModelAndView list (@RequestParam Long idSportCenter,
								   @RequestParam(value="update_done", defaultValue="false") boolean updateDone,
								   @RequestParam(value="add_done", defaultValue="false") boolean addDone,
								   @RequestParam(value="remove_done", defaultValue="false") boolean removeDone,
								   @RequestParam(value="remove_undone", defaultValue="false") boolean removeUndone) {
		ModelAndView modelAndView = new ModelAndView("courts_list");
		SportCenterForm sportCenterForm = sportsCenterManager.querySportCentersById(idSportCenter);
		modelAndView.addObject("sportCenter", sportCenterForm);
		modelAndView.addObject("courts", sportCenterCourtManager.querySportCourts(idSportCenter));
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("remove_undone", removeUndone);
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView add(@RequestParam Long idCenter) {
		ModelAndView modelAndView = new ModelAndView("courts_add");
		SportCenterForm sportCenterForm = sportsCenterManager.querySportCentersById(idCenter);
		SportsCourtForm sportsCourtForm = new SportsCourtForm();
		sportsCourtForm.setIdCenter(idCenter);
		sportsCourtForm.setNameCenter(sportCenterForm.getName());
		modelAndView.addObject("my_form", sportsCourtForm);
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(@ModelAttribute("my_form") SportsCourtForm sportsCourtForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCourtFormValidator.validate(sportsCourtForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportsCourtForm);
			modelAndView.setViewName("courts_add");
		} else {
			try {
				sportCenterCourtManager.addSportCourt(sportsCourtForm);
			} catch (Exception e) {
				logger.error(e);
			}
			String viewName = "redirect:/courts/list";
			viewName += "?add_done=true";
			viewName += "&idSportCenter=" + sportsCourtForm.getIdCenter();
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public ModelAndView doDelete(@RequestParam Long idCourt, @RequestParam Long idCenter) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String viewName = null;
		viewName = "redirect:/courts/list";
		if (sportCenterCourtManager.isElegibleForDelete(idCourt)) {
			sportCenterCourtManager.removeSportCourt(idCourt);
			viewName += "?remove_done=true";
		} else {
			viewName += "?remove_undone=true";
		}
		viewName += "&idSportCenter=" + idCenter;
		modelAndView.setViewName(viewName);
		return modelAndView;
	}

	@RequestMapping("/update")
	public ModelAndView update(@RequestParam Long idCourt) {
		ModelAndView modelAndView = new ModelAndView("courts_update");
		SportCenterCourt court = sportCenterCourtManager.querySportCourt(idCourt);
		SportsCourtForm sportsCourtForm = new SportsCourtForm(court);
		modelAndView.addObject("my_form", sportsCourtForm);
		return modelAndView;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@ModelAttribute("my_form") SportsCourtForm sportsCourtForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCourtFormValidator.validate(sportsCourtForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportsCourtForm);
			modelAndView.setViewName("courts_update");
		} else {
			try {
				sportCenterCourtManager.updateSportCourt(sportsCourtForm);
			} catch (Exception e) {
				logger.error(e);
			}
			String viewName = "redirect:/courts/list";
			viewName += "?update_done=true";
			viewName += "&idSportCenter=" + sportsCourtForm.getIdCenter();
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}
}
