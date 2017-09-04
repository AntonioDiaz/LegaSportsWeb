package com.adiaz.controllers;

import com.adiaz.forms.TownForm;
import com.adiaz.forms.validators.TownFormValidator;
import com.adiaz.services.TownManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by toni on 14/07/2017.
 */
@Controller
@SessionAttributes({"towns"})
@RequestMapping("/towns")
public class TownController {

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TownController.class);

	@Autowired
	TownManager townManager;
	@Autowired
	TownFormValidator townFormValidator;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "update_done", defaultValue = "false") boolean updateDone,
			@RequestParam(value = "add_done", defaultValue = "false") boolean addDone,
			@RequestParam(value = "remove_done", defaultValue = "false") boolean removeDone) {
		ModelAndView modelAndView = new ModelAndView("towns_list");
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		/* It is necessary to update the towns list in session.*/
		modelAndView.addObject("towns", townManager.queryAll());
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView modelAndView = new ModelAndView("towns_add");
		modelAndView.addObject("my_form", new TownForm());
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(@ModelAttribute("my_form") TownForm townForm, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		townFormValidator.validate(townForm, bindingResult);
		if (!bindingResult.hasErrors()) {
			townManager.add(townForm);
			modelAndView.setViewName("redirect:/towns/list?add_done=true");
		} else {
			modelAndView.addObject("my_form", townForm);
			modelAndView.setViewName("towns_add");
		}
		return modelAndView;
	}

	@RequestMapping("/delete")
	public String doDelete(@RequestParam(value = "idTown") Long id) throws Exception {
		townManager.remove(id);
		return "redirect:/towns/list?remove_done=true";
	}

	@RequestMapping("/update")
	public ModelAndView update(@RequestParam(value = "idTown") Long id) throws Exception {
		ModelAndView modelAndView = new ModelAndView("towns_update");
		TownForm townForm = townManager.queryById(id);
		modelAndView.addObject("my_form", townForm);
		return modelAndView;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@ModelAttribute("my_form") TownForm townForm, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		townFormValidator.validate(townForm, bindingResult);
		if (!bindingResult.hasErrors()) {
			townManager.update(townForm.getId(), townForm);
			modelAndView.setViewName("redirect:/towns/list?update_done=true");
		} else {
			modelAndView.addObject("my_form", townForm);
			modelAndView.setViewName("towns_update");
		}
		return modelAndView;
	}

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam(value = "idTown") Long id) throws Exception {
		ModelAndView modelAndView = new ModelAndView("towns_view");
		TownForm townForm = townManager.queryById(id);
		modelAndView.addObject("my_form", townForm);
		return modelAndView;
	}
}
