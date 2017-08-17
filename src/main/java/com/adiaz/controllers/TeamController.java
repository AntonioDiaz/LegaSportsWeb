package com.adiaz.controllers;

import com.adiaz.entities.Club;
import com.adiaz.entities.Team;
import com.adiaz.forms.TeamFilterForm;
import com.adiaz.forms.TeamForm;
import com.adiaz.forms.validators.TeamFormValidator;
import com.adiaz.services.ClubManager;
import com.adiaz.services.TeamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.adiaz.utils.MuniSportsUtils.getActiveUser;

/**
 * Created by toni on 25/07/2017.
 */
@Controller
@RequestMapping("/team")
@SessionAttributes ("form_filter")
public class TeamController {

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TeamController.class);

	@Autowired
	TeamManager teamManager;

	@Autowired
	TeamFormValidator teamFormValidator;

	@Autowired
	ClubManager clubManager;

	@ModelAttribute("club_list")
	public List<Club> addClubList(){
		return clubManager.queryAll();
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("team_list");
		modelAndView.addObject("form_filter", new TeamFilterForm());
		return modelAndView;
	}

	@RequestMapping("/doFilter")
	public ModelAndView doFilter(
			@ModelAttribute("form_filter") TeamFilterForm filterForm,
			@RequestParam(value = "update_done", defaultValue = "false") boolean updateDone,
			@RequestParam(value = "add_done", defaultValue = "false") boolean addDone,
			@RequestParam(value = "delete_done", defaultValue = "false") boolean deleteDone){
		ModelAndView modelAndView = new ModelAndView("team_list");
		modelAndView.addObject("form_filter", filterForm);
		List<Team> teams = teamManager.queryByFilter(filterForm);
		modelAndView.addObject("teamList", teams);
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("delete_done", deleteDone);
		return modelAndView;
	}

	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("team_add");
		modelAndView.addObject("my_form", new TeamForm());
		return modelAndView;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(@ModelAttribute("my_form") TeamForm teamForm, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		teamFormValidator.validate(teamForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", teamForm);
			modelAndView.setViewName("team_add");
		} else {
			if (!getActiveUser().isAdmin()) {
				teamForm.setIdTown(getActiveUser().getTownEntity().getId());
			}
			teamManager.add(teamForm);
			String viewName = "redirect:/team/doFilter?add_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/update")
	public ModelAndView update(@RequestParam Long id){
		ModelAndView modelAndView = new ModelAndView("team_update");
		modelAndView.addObject("my_form", teamManager.queryById(id));
		return modelAndView;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@ModelAttribute("my_form") TeamForm teamForm, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		teamFormValidator.validate(teamForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", teamForm);
			modelAndView.setViewName("team_update");
		} else {
			if (!getActiveUser().isAdmin()) {
				teamForm.setIdTown(getActiveUser().getTownEntity().getId());
			}
			teamManager.update(teamForm);
			String viewName = "redirect:/team/doFilter?update_done=true";
			modelAndView.setViewName(viewName);
		}
		return modelAndView;
	}

	@RequestMapping("/doDelete")
	public String doDelete(@RequestParam Long id) throws Exception {
		// TODO: 25/07/2017 validate user is admin or the town of the town is the same than the user.
		teamManager.remove(id);
		return "redirect:/team/doFilter?delete_done=true";
	}

	@RequestMapping("/view")
	public ModelAndView view(@RequestParam Long id){
		ModelAndView modelAndView = new ModelAndView("team_view");
		modelAndView.addObject("my_form", teamManager.queryById(id));
		return modelAndView;
	}


}