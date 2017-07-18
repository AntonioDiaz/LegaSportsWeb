package com.adiaz.controllers;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCourt;
import com.adiaz.forms.SportCenterForm;
import com.adiaz.forms.SportCenterFormValidator;
import com.adiaz.forms.SportCourtFormValidator;
import com.adiaz.forms.SportsCourtForm;
import com.adiaz.services.SportCenterManager;
import com.adiaz.services.SportCourtManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.adiaz.utils.UtilsLegaSport.getActiveUser;

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
		List<SportCenter> centers;
		if (getActiveUser().isAdmin()) {
			centers = sportsCenterManager.querySportCenters();
		} else {
			centers = sportsCenterManager.querySportCenters(getActiveUser().getTown().getId());
		}
		modelAndView.addObject("centers", centers);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		return modelAndView;
	}
	
	@RequestMapping("/add")
	public ModelAndView addCenter() {
		ModelAndView modelAndView = new ModelAndView("center_add");
		SportCenterForm sportCenterForm = new SportCenterForm();
		if (!getActiveUser().isAdmin()) {
			sportCenterForm.setIdTown(getActiveUser().getTown().getId());
		}
		modelAndView.addObject("my_form", sportCenterForm );
		return modelAndView;
	}
	
	@RequestMapping("/doAdd")
	public ModelAndView doAddCenter(
			@ModelAttribute("my_form") SportCenterForm sportCenterForm,
			BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCenterFormValidator.validate(sportCenterForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportCenterForm);
			modelAndView.setViewName("center_add");
		} else {
			try {
				/* in case malicious behavior */
				if (!getActiveUser().isAdmin()) {
					sportCenterForm.setIdTown(getActiveUser().getTown().getId());
				}
				sportsCenterManager.addSportCenter(sportCenterForm);
			} catch (Exception e) {
				// TODO: 17/07/2017 add error page.
				logger.error(e);
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
		SportCenterForm sportCenterForm = sportsCenterManager.querySportCentersById(id);
		if (!getActiveUser().isAdmin()) {
			sportCenterForm.setIdTown(getActiveUser().getTown().getId());
		}
		modelAndView.addObject("my_form", sportCenterForm);

		return modelAndView;
	}
	
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdateCenter(@ModelAttribute("my_form") SportCenterForm sportCenterForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCenterFormValidator.validate(sportCenterForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportCenterForm);
			modelAndView.setViewName("center_update");
		} else {
			try {
				/* in case malicious behavior */
				if (!getActiveUser().isAdmin()) {
					sportCenterForm.setIdTown(getActiveUser().getTown().getId());
				}
				sportsCenterManager.updateSportCenter(sportCenterForm);
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
			boolean validDelete = true;
			/* in case malicious behavior */
			if (!getActiveUser().isAdmin()) {
				SportCenterForm center = sportsCenterManager.querySportCentersById(id);
				validDelete = center.getIdTown()==getActiveUser().getTown().getId();
			}
			if (validDelete) {
				sportsCenterManager.removeSportCenter(id);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		String viewName = "redirect:/center/list";
		viewName += "?remove_done=true";
		modelAndView.setViewName(viewName);
		return modelAndView;
	}
	
	@RequestMapping("/listCourts")
	public ModelAndView listCourts(@RequestParam Long idSportCenter,
			@RequestParam(value="update_done", defaultValue="false") boolean updateDone,
			@RequestParam(value="add_done", defaultValue="false") boolean addDone,
			@RequestParam(value="remove_done", defaultValue="false") boolean removeDone) {
		ModelAndView modelAndView = new ModelAndView("courts_list");
		SportCenterForm sportCenterForm = sportsCenterManager.querySportCentersById(idSportCenter);
		modelAndView.addObject("sportCenter", sportCenterForm);
		modelAndView.addObject("courts", sportCourtManager.querySportCourts(idSportCenter));
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		return modelAndView;
	}
	
	@RequestMapping("/addCourt")
	public ModelAndView addCourt(@RequestParam Long idCenter) {
		ModelAndView modelAndView = new ModelAndView("courts_add");
		SportCenterForm sportCenterForm = sportsCenterManager.querySportCentersById(idCenter);
		SportsCourtForm sportsCourtForm = new SportsCourtForm();
		sportsCourtForm.setIdCenter(idCenter);
		sportsCourtForm.setNameCenter(sportCenterForm.getName());
		modelAndView.addObject("my_form", sportsCourtForm);
		return modelAndView;
	}
	
	@RequestMapping("/doAddCourt")
	public ModelAndView doAddCourt(@ModelAttribute("my_form") SportsCourtForm sportsCourtForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCourtFormValidator.validate(sportsCourtForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportsCourtForm);
			modelAndView.setViewName("courts_add");
		} else {
			try {
				sportCourtManager.addSportCourt(sportsCourtForm);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			String viewName = "redirect:/center/listCourts";
			viewName += "?add_done=true";
			viewName += "&idSportCenter=" + sportsCourtForm.getIdCenter();
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/doDeleteCourt")
	public ModelAndView doDeleteCourt(@RequestParam Long idCourt, @RequestParam Long idCenter) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			sportCourtManager.removeSportCourt(idCourt);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		String viewName = "redirect:/center/listCourts";
		viewName += "?remove_done=true";
		viewName += "&idSportCenter=" + idCenter;
		modelAndView.setViewName(viewName);
		return modelAndView;
	}
	
	@RequestMapping("/updateCourt")
	public ModelAndView updateCourt(@RequestParam Long idCourt) {
		ModelAndView modelAndView = new ModelAndView("courts_update");
		SportCourt court = sportCourtManager.querySportCourt(idCourt);
		SportsCourtForm sportsCourtForm = new SportsCourtForm(court);
		modelAndView.addObject("my_form", sportsCourtForm);
		return modelAndView;
	}
	
	@RequestMapping("/doUpdateCourt")
	public ModelAndView doUpdateCourt(@ModelAttribute("my_form") SportsCourtForm sportsCourtForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		sportCourtFormValidator.validate(sportsCourtForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sportsCourtForm);
			modelAndView.setViewName("courts_update");
		} else {
			try {
				sportCourtManager.updateSportCourt(sportsCourtForm);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			String viewName = "redirect:/center/listCourts";
			viewName += "?update_done=true";
			viewName += "&idSportCenter=" + sportsCourtForm.getIdCenter();
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/view")
	public ModelAndView viewCenter(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView("center_view");
		SportCenterForm sportCenterForm = sportsCenterManager.querySportCentersById(id);
		modelAndView.addObject("my_form", sportCenterForm);
		return modelAndView;
	}
}