//TODO DELETE??
package com.adiaz.controllers;

import com.adiaz.entities.Sport;
import com.adiaz.forms.validators.SportsFormValidator;
import com.adiaz.services.SportsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes({"sports"})
@RequestMapping("/sports")
public class SportsController {
	//private static final Logger logger = Logger.getLogger(SportsController.class);

	@Autowired
	SportsManager sportsManager;
	@Autowired
	SportsFormValidator sportsFormValidator;


	@RequestMapping("/list")
	public ModelAndView getSportsList(
			@RequestParam(value = "add_done", defaultValue = "false") boolean addDone,
			@RequestParam(value = "update_done", defaultValue = "false") boolean updateDone,
			@RequestParam(value = "remove_done", defaultValue = "false") boolean removeDone,
			@RequestParam(value = "remove_undone", defaultValue = "false") boolean removeUndone) {
		ModelAndView modelAndView = new ModelAndView("sports_list");
		List<Sport> sports = sportsManager.querySports();
		modelAndView.addObject("sports", sports);
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("remove_undone", removeUndone);
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("sports_add");
		modelAndView.addObject("my_form", new Sport());
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(@ModelAttribute("my_form") Sport sport, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		sportsFormValidator.validate(sport, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sport);
			modelAndView.setViewName("sports_add");
		} else {
			sportsManager.add(sport);
			String viewName = "redirect:/sports/list?add_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam Long id) {
		ModelAndView modelAndView = new ModelAndView("sports_view");
		Sport sport = sportsManager.querySportsById(id);
		modelAndView.addObject("my_form", sport);
		return modelAndView;
	}


	@RequestMapping("/update")
	public ModelAndView update(@RequestParam Long id){
		ModelAndView modelAndView = new ModelAndView("sports_update");
		Sport sport = sportsManager.querySportsById(id);
		modelAndView.addObject("my_form", sport);
		return modelAndView;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@ModelAttribute("my_form") Sport sport, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		sportsFormValidator.validate(sport, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", sport);
			modelAndView.setViewName("sports_update");
		} else {
			sportsManager.update(sport);
			String viewName = "redirect:/sports/list?update_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public String doDelete(@RequestParam Long id) throws Exception {
		if (sportsManager.remove(id)) {
			return "redirect:/sports/list?remove_done=true";
		} else {
			return "redirect:/sports/list?remove_undone=true";
		}
	}
}
