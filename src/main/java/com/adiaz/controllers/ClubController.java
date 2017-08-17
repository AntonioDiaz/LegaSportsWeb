package com.adiaz.controllers;

import com.adiaz.forms.ClubForm;
import com.adiaz.forms.validators.ClubFormValidator;
import com.adiaz.services.ClubManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.adiaz.utils.MuniSportsUtils.getActiveUser;

/**
 * Created by toni on 24/07/2017.
 */
@Controller
@RequestMapping("/club")
public class ClubController {

	private static final Logger logger = Logger.getLogger(ClubController.class);

	@Autowired
	ClubManager clubManager;

	@Autowired
	ClubFormValidator clubFormValidator;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "update_done", defaultValue = "false") boolean updateDone,
			@RequestParam(value = "add_done", defaultValue = "false") boolean addDone,
			@RequestParam(value = "delete_done", defaultValue = "false") boolean deleteDone) {
		ModelAndView modelAndView = new ModelAndView("club_list");
		modelAndView.addObject("clubList", clubManager.queryAll());
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("delete_done", deleteDone);
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("club_add");
		modelAndView.addObject("my_form", new ClubForm());
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(@ModelAttribute("my_form") ClubForm clubForm, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		clubFormValidator.validate(clubForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", clubForm);
			modelAndView.setViewName("club_add");
		} else {
			if (!getActiveUser().isAdmin()) {
				clubForm.setIdTown(getActiveUser().getTownEntity().getId());
			}
			clubManager.add(clubForm);
			String viewName = "redirect:/club/list?add_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}


	@RequestMapping("/update")
	public ModelAndView update(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView("club_update");
		modelAndView.addObject("my_form", clubManager.queryById(id));
		return modelAndView;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@ModelAttribute("my_form") ClubForm clubForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		clubFormValidator.validate(clubForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", clubForm);
			modelAndView.setViewName("club_update");
		} else {
			if (!getActiveUser().isAdmin()) {
				clubForm.setIdTown(getActiveUser().getTownEntity().getId());
			}
			try {
				clubManager.update(clubForm);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			String viewName = "redirect:/club/list?update_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView("club_view");
		modelAndView.addObject("my_form", clubManager.queryById(id));
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public String doDelete(@RequestParam Long id) throws Exception {
		// TODO: 25/07/2017 validate user is admin or the town of the town is the same than the user. 
		clubManager.remove(id);
		return "redirect:/club/list?delete_done=true";
	}
}