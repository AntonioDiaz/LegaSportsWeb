package com.adiaz.controllers;

import com.adiaz.entities.Center;
import com.adiaz.entities.Court;
import com.adiaz.forms.CourtForm;
import com.adiaz.forms.validators.CourtFormValidator;
import com.adiaz.services.CourtManager;
import com.adiaz.services.CenterManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by toni on 20/09/2017.
 */
@Controller
@SessionAttributes ("centerSession")
@RequestMapping(value="/courts")
public class CourtsController {

	private static final Logger logger = Logger.getLogger(CourtsController.class);
	@Autowired
	CourtManager courtManager;
	@Autowired
	CourtFormValidator courtFormValidator;
	@Autowired
	CenterManager sportsCenterManager;

	@RequestMapping("/initlist")
	public ModelAndView list (@RequestParam Long idCenter){
		ModelAndView modelAndView = new ModelAndView();
		Center center = sportsCenterManager.queryById(idCenter);
		modelAndView.addObject("centerSession", center);
		modelAndView.setViewName("redirect:/courts/list");
		return modelAndView;
	}

	@RequestMapping("/list")
	public ModelAndView list (		@ModelAttribute("centerSession") Center center,
								   @RequestParam(value="update_done", defaultValue="false") boolean updateDone,
								   @RequestParam(value="add_done", defaultValue="false") boolean addDone,
								   @RequestParam(value="remove_done", defaultValue="false") boolean removeDone,
								   @RequestParam(value="remove_undone", defaultValue="false") boolean removeUndone) {
		ModelAndView modelAndView = new ModelAndView("courts_list");
		modelAndView.addObject("courts", courtManager.querySportCourts(center.getId()));
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("remove_undone", removeUndone);
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView add(@ModelAttribute("centerSession") Center center) {
		ModelAndView modelAndView = new ModelAndView("courts_add");
		CourtForm courtForm = new CourtForm();
		courtForm.setIdCenter(center.getId());
		modelAndView.addObject("my_form", courtForm);
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(@ModelAttribute("my_form") CourtForm courtForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		courtFormValidator.validate(courtForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", courtForm);
			modelAndView.setViewName("courts_add");
		} else {
			try {
				courtManager.addSportCourt(courtForm);
			} catch (Exception e) {
				logger.error(e);
			}
			String viewName = "redirect:/courts/list";
			viewName += "?add_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public ModelAndView doDelete(@RequestParam Long idCourt) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String viewName = null;
		viewName = "redirect:/courts/list";
		if (courtManager.isElegibleForDelete(idCourt)) {
			courtManager.removeSportCourt(idCourt);
			viewName += "?remove_done=true";
		} else {
			viewName += "?remove_undone=true";
		}
		modelAndView.setViewName(viewName);
		return modelAndView;
	}

	@RequestMapping("/update")
	public ModelAndView update(@RequestParam Long idCourt) {
		ModelAndView modelAndView = new ModelAndView("courts_update");
		Court court = courtManager.querySportCourt(idCourt);
		CourtForm courtForm = new CourtForm(court);
		modelAndView.addObject("my_form", courtForm);
		return modelAndView;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@ModelAttribute("my_form") CourtForm courtForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		courtFormValidator.validate(courtForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", courtForm);
			modelAndView.setViewName("courts_update");
		} else {
			try {
				courtManager.updateSportCourt(courtForm);
			} catch (Exception e) {
				logger.error(e);
			}
			String viewName = "redirect:/courts/list?update_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}
}
