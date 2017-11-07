package com.adiaz.controllers;

import com.adiaz.entities.Center;
import com.adiaz.forms.CenterForm;
import com.adiaz.forms.validators.CenterFormValidator;
import com.adiaz.services.CenterManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.adiaz.utils.LocalSportsUtils.getActiveUser;

@Controller
@RequestMapping (value="/centers")
public class CentersController {

	private static final Logger logger = Logger.getLogger(CentersController.class);
	
	@Autowired
	CenterManager sportsCenterManager;
	@Autowired
	CenterFormValidator centerFormValidator;

	@RequestMapping("/list")
	public ModelAndView centerList(
			@RequestParam(value="update_done", defaultValue="false") boolean updateDone,
			@RequestParam(value="add_done", defaultValue="false") boolean addDone,
			@RequestParam(value="remove_done", defaultValue="false") boolean removeDone,
			@RequestParam(value="remove_undone", defaultValue="false") boolean removeUndone) {
		ModelAndView modelAndView = new ModelAndView("center_list");
		List<Center> centers;
		if (getActiveUser().isAdmin()) {
			centers = sportsCenterManager.querySportCenters();
		} else {
			centers = sportsCenterManager.querySportCenters(getActiveUser().getTownEntity().getId());
		}
		modelAndView.addObject("centers", centers);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("remove_undone", removeUndone);
		return modelAndView;
	}
	
	@RequestMapping("/add")
	public ModelAndView addCenter() {
		ModelAndView modelAndView = new ModelAndView("center_add");
		CenterForm centerForm = new CenterForm();
		if (!getActiveUser().isAdmin()) {
			centerForm.setIdTown(getActiveUser().getTownEntity().getId());
		}
		modelAndView.addObject("my_form", centerForm);
		return modelAndView;
	}
	
	@RequestMapping("/doAdd")
	public ModelAndView doAddCenter(
			@ModelAttribute("my_form") CenterForm centerForm,
			BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		centerFormValidator.validate(centerForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", centerForm);
			modelAndView.setViewName("center_add");
		} else {
			try {
				/* in case malicious behavior */
				if (!getActiveUser().isAdmin()) {
					centerForm.setIdTown(getActiveUser().getTownEntity().getId());
				}
				sportsCenterManager.addCenter(centerForm);
			} catch (Exception e) {
				// TODO: 17/07/2017 add error page.
				logger.error(e.getMessage(), e);
			}
			String viewName = "redirect:/centers/list";
			viewName += "?add_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}
	
	@RequestMapping("/update")
	public ModelAndView updateCenter(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView("center_update");
		CenterForm centerForm = sportsCenterManager.queryFormById(id);
		if (!getActiveUser().isAdmin()) {
			centerForm.setIdTown(getActiveUser().getTownEntity().getId());
		}
		modelAndView.addObject("my_form", centerForm);

		return modelAndView;
	}
	
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdateCenter(@ModelAttribute("my_form") CenterForm centerForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		centerFormValidator.validate(centerForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", centerForm);
			modelAndView.setViewName("center_update");
		} else {
			try {
				/* in case malicious behavior */
				if (!getActiveUser().isAdmin()) {
					centerForm.setIdTown(getActiveUser().getTownEntity().getId());
				}
				sportsCenterManager.updateSportCenter(centerForm);
			} catch (Exception e) {
				logger.error(e);
			}
			String viewName = "redirect:/centers/list";
			viewName += "?update_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public ModelAndView doDelete(@RequestParam Long id) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		/* in case of malicious behavior */
		boolean validDelete = true;
		if (!getActiveUser().isAdmin()) {
			CenterForm center = sportsCenterManager.queryFormById(id);
			validDelete = center.getIdTown().equals(getActiveUser().getTownEntity().getId());
		}
		if (validDelete) {
			String viewName = "redirect:/centers/list";
			if (sportsCenterManager.isElegibleForDelete(id)) {
				sportsCenterManager.removeSportCenter(id);
				viewName += "?remove_done=true";
			} else {
				viewName += "?remove_undone=true";
			}
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}
	


	@RequestMapping("/view")
	public ModelAndView viewCenter(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView("center_view");
		CenterForm centerForm = sportsCenterManager.queryFormById(id);
		modelAndView.addObject("my_form", centerForm);
		return modelAndView;
	}
}