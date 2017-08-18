package com.adiaz.controllers;

import com.adiaz.entities.*;
import com.adiaz.forms.*;
import com.adiaz.forms.validators.CompetitionsFormValidator;
import com.adiaz.forms.validators.GenerateCalendarFormValidator;
import com.adiaz.services.*;

import static com.adiaz.utils.MuniSportsUtils.getActiveUser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping ("/competitions")
@SessionAttributes ({"form_filter", "courts"})
public class CompetitionsController {
	private static final Logger logger = Logger.getLogger(CompetitionsController.class);
	@Autowired CompetitionsManager competitionsManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	@Autowired CompetitionsFormValidator competitionsFormValidator;
	@Autowired GenerateCalendarFormValidator generateCalendarFormValidator;
	@Autowired SportCenterCourtManager sportCenterCourtManager;
	@Autowired TeamManager teamManager;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="remove_done", defaultValue="false") boolean removeDone){
		ModelAndView modelAndView = new ModelAndView("competitions_list");
		modelAndView.addObject("form_filter", new CompetitionsFilterForm());
		modelAndView.addObject("remove_done", removeDone);
		return modelAndView;
	}

	@RequestMapping("/doFilter")
	public ModelAndView doFilter(
			@ModelAttribute("form_filter") CompetitionsFilterForm competitionsFilterForm){
		ModelAndView modelAndView = new ModelAndView("competitions_list");
		modelAndView.addObject("form_filter", competitionsFilterForm);
		List<Competition> competitions = competitionsManager.queryCompetitions(competitionsFilterForm);
		modelAndView.addObject("competitions", competitions);
		return modelAndView;
	}

	@RequestMapping ("/add")
	public ModelAndView addCompetitions() {
		ModelAndView modelAndView = new ModelAndView("competitions_add");
		CompetitionsForm competitionsForm = new CompetitionsForm();
		if (!getActiveUser().isAdmin()) {
			competitionsForm.setIdTown(getActiveUser().getTownEntity().getId());
		}
		modelAndView.addObject("my_form", competitionsForm);
		return modelAndView;
	}
	
	@RequestMapping ("/doAdd")
	public ModelAndView doAddCompetition(@ModelAttribute("my_form") CompetitionsForm competitionsForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		this.competitionsFormValidator.validate(competitionsForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", competitionsForm);
			modelAndView.setViewName("competitions_add");
		} else {
			try {
				if (!getActiveUser().isAdmin()) {
					competitionsForm.setIdTown(getActiveUser().getTownEntity().getId());
				}
				competitionsManager.add(competitionsForm);
				modelAndView.setViewName("redirect:/competitions/list?add_done=true");
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return modelAndView;
	}
	
	@RequestMapping("/doRemove")
	public String doRemoveCompetition(@RequestParam(value = "idCompetition") Long idCompetition) {
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		try {
			/* in case malicious behavior */
			boolean validDelete = true;
			if (!getActiveUser().isAdmin()) {
				CompetitionsForm competitionsForm = competitionsManager.queryCompetitionsById(idCompetition);
				validDelete = competitionsForm.getIdTown()==getActiveUser().getTownEntity().getId();
			}
			if (validDelete) {
				competitionsManager.remove(competition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/competitions/list?remove_done=true";
	}
	
	@RequestMapping ("/viewCalendar")
	public ModelAndView viewCalendar(@RequestParam(value = "idCompetition") Long idCompetition,
									 @RequestParam(value="add_done", defaultValue="false") boolean addDone,
									 @RequestParam(value="update_done", defaultValue="false") boolean updateDone,
									 @RequestParam(value="publish_done", defaultValue="false") boolean publishDone,
									 @RequestParam(value="publish_none", defaultValue="false") boolean publishNone) {
		ModelAndView modelAndView = new ModelAndView("competitions_calendar");
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		List<MatchForm> matchesList = matchesManager.queryMatchesFormWorkingCopy(idCompetition);
		Integer howManyWeek = matchesManager.howManyWeek(matchesList);
		List<SportCenterCourt> courts = sportCenterCourtManager.querySportCourtsByTownAndSport(
				competition.getTownEntity().getId(), competition.getSportEntity().getId());
		modelAndView.addObject("competition", competition);
		modelAndView.addObject("matches_list", matchesList);
		modelAndView.addObject("weeks_count", howManyWeek);
		modelAndView.addObject("courts", courts);
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("publish_done", publishDone);
		modelAndView.addObject("publish_none", publishNone);
		return modelAndView;
	}
	
	@RequestMapping ("/viewClassification")
	public ModelAndView viewClassification(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_classification");
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		List<ClassificationEntry> classificationList = classificationManager.queryClassificationByCompetition(idCompetition);
		modelAndView.addObject("competition", competition);
		modelAndView.addObject("classification_list", classificationList);
		return modelAndView;
	}	
	
	@RequestMapping ("/loadCalendar")
	public ModelAndView loadCalendar(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_load_calendar");
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		modelAndView.addObject("competition", competition);
		GenerateCalendarForm form = new GenerateCalendarForm();
		form.setIdCompetition(idCompetition);
		modelAndView.addObject("my_form", form);
		List<Team> teams = teamManager.queryByCompetition(idCompetition);
		modelAndView.addObject("teams_available", teams);
		return modelAndView;
	}

	@RequestMapping ("/doLoadCalendar")
	public ModelAndView doLoadCalendar(@ModelAttribute("my_form") GenerateCalendarForm form, BindingResult bindingResult) throws Exception {
				ModelAndView modelAndView = new ModelAndView();
		generateCalendarFormValidator.validate(form, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("competitions_load_calendar");
			Competition competition = competitionsManager.queryCompetitionsByIdEntity(form.getIdCompetition());
			modelAndView.addObject("competition", competition);
			modelAndView.addObject("teams_available", teamManager.queryByCompetition(competition.getId()));
			modelAndView.addObject("my_form", form);
		} else {

			//competitionsManager.updateAddTeams();
			matchesManager.generateCalendar(form);
			String viewStr = "redirect:/competitions/viewCalendar?idCompetition=" + form.getIdCompetition();
			modelAndView.setViewName(viewStr);
		}
		return modelAndView;
	}

	@RequestMapping ("/update")
	public ModelAndView update(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_update");
		CompetitionsForm competitionsForm = competitionsManager.queryCompetitionsById(idCompetition);
		modelAndView.addObject("my_form", competitionsForm);
		return modelAndView;
	}

	@RequestMapping ("/doUpdate")
	public ModelAndView doUpdateCompetition(@ModelAttribute("my_form") CompetitionsForm competitionsForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		this.competitionsFormValidator.validate(competitionsForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", competitionsForm);
			modelAndView.setViewName("competitions_update");
		} else {
			try {
				competitionsManager.update(competitionsForm.getId(), competitionsForm);
				String viewNameStr =
						"redirect:/competitions/viewCalendar?" +
						"idCompetition=" + competitionsForm.getId() +
						"&update_done=true";
				modelAndView.setViewName(viewNameStr);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return modelAndView;
	}

	@RequestMapping("/publishCalendar")
	public String publishCalendar(@RequestParam(value = "idCompetition") Long idCompetition) throws Exception {
		String redirectTo = "redirect:/competitions/viewCalendar?idCompetition=" +  idCompetition;
		if (matchesManager.checkUpdatesToPublish(idCompetition)) {
			matchesManager.updatePublishedMatches(idCompetition);
			Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
			competition.setLastUpdate(new Date());
			competitionsManager.update(competition);
			redirectTo += "&publish_done=true";
		} else {
			redirectTo += "&publish_none=true";
		}
		return redirectTo;
	}
}
