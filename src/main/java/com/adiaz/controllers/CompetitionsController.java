package com.adiaz.controllers;

import com.adiaz.entities.*;
import com.adiaz.forms.*;
import com.adiaz.forms.validators.CompetitionsFormValidator;
import com.adiaz.forms.validators.GenerateCalendarFormValidator;
import com.adiaz.forms.validators.SanctionFormValidator;
import com.adiaz.services.*;

import static com.adiaz.utils.LocalSportsUtils.getActiveUser;

import com.adiaz.utils.LocalSportsConstants;
import com.adiaz.utils.LocalSportsUtils;
import com.googlecode.objectify.Ref;
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
@SessionAttributes ({"form_filter", "courts", "competition_session"})
public class CompetitionsController {
	private static final Logger logger = Logger.getLogger(CompetitionsController.class);
	@Autowired CompetitionsManager competitionsManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	@Autowired CompetitionsFormValidator competitionsFormValidator;
	@Autowired GenerateCalendarFormValidator generateCalendarFormValidator;
	@Autowired CourtManager courtManager;
	@Autowired TeamManager teamManager;
	@Autowired SanctionsManager sanctionsManager;
	@Autowired SanctionFormValidator sanctionFormValidator;
	@Autowired ParametersManager parametersManager;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="remove_done", defaultValue="false") boolean removeDone){
		ModelAndView modelAndView = new ModelAndView("competitions_list");
		CompetitionsFilterForm competitionsFilterForm = new CompetitionsFilterForm();
		if (!getActiveUser().isAdmin()) {
			competitionsFilterForm.setIdTown(getActiveUser().getTownEntity().getId());
		}
		modelAndView.addObject("form_filter", competitionsFilterForm);
		modelAndView.addObject("remove_done", removeDone);
		return modelAndView;
	}

	@RequestMapping("/doFilter")
	public ModelAndView doFilter(@ModelAttribute("form_filter") CompetitionsFilterForm competitionsFilterForm){
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
	public String doRemoveCompetition(@ModelAttribute("competition_session") Competition competition) {
		try {
			/* in case malicious behavior */
			boolean validDelete = true;
			if (!getActiveUser().isAdmin()) {
				CompetitionsForm competitionsForm = competitionsManager.queryCompetitionsById(competition.getId());
				validDelete = competitionsForm.getIdTown().equals(getActiveUser().getTownEntity().getId());
			}
			if (validDelete) {
				competitionsManager.remove(competition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/competitions/list?remove_done=true";
	}

	@RequestMapping ("/view")
	public ModelAndView view(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("redirect:/competitions/viewCalendar");
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		modelAndView.addObject("competition_session", competition);
		return modelAndView;
	}

	@RequestMapping ("/viewCalendar")
	public ModelAndView viewCalendar(
			@ModelAttribute("competition_session") Competition competition,
			@RequestParam(value = "add_done", defaultValue = "false") boolean addDone,
			@RequestParam(value = "update_done", defaultValue = "false") boolean updateDone,
			@RequestParam(value = "publish_done", defaultValue = "false") boolean publishDone,
			@RequestParam(value = "publish_none", defaultValue = "false") boolean publishNone,
			@RequestParam(value = "notification_error", defaultValue = "false") boolean notificationError) {
		ModelAndView modelAndView = new ModelAndView("competitions_calendar");
		List<MatchForm> matchesList = matchesManager.queryMatchesFormWorkingCopy(competition.getId());
		Integer howManyWeek = matchesManager.howManyWeek(matchesList);
		List<Court> courts = courtManager.querySportCourtsByTownAndSport(
				competition.getTownEntity().getId(), competition.getSportEntity().getId());
		modelAndView.addObject("matches_list", matchesList);
		modelAndView.addObject("weeks_count", howManyWeek);
		modelAndView.addObject("courts", courts);
		modelAndView.addObject("states", LocalSportsConstants.MATCH_STATE.values());
		modelAndView.addObject("add_done", addDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("publish_done", publishDone);
		modelAndView.addObject("publish_none", publishNone);
		modelAndView.addObject("notification_error", notificationError);
		return modelAndView;
	}
	
	@RequestMapping ("/viewClassification")
	public ModelAndView viewClassification(@ModelAttribute("competition_session") Competition competition) {
		ModelAndView modelAndView = new ModelAndView("competitions_classification");
		List<ClassificationEntry> classificationList = classificationManager.queryClassificationByCompetition(competition.getId());
		List<Sanction> sanctionsList = sanctionsManager.querySanctionsByIdCompetition(competition.getId());
		modelAndView.addObject("classification_list", classificationList);
		modelAndView.addObject("sanctions_list", sanctionsList);
		return modelAndView;
	}	
	
	@RequestMapping ("/loadCalendar")
	public ModelAndView loadCalendar(@ModelAttribute("competition_session") Competition competition) {
		Long id = competition.getId();
		ModelAndView modelAndView = new ModelAndView("competitions_load_calendar");
		modelAndView.addObject("competition", competition);
		GenerateCalendarForm form = new GenerateCalendarForm();
		form.setIdCompetition(id);
		modelAndView.addObject("my_form", form);
		List<Team> teams = teamManager.queryByCompetition(id);
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
			classificationManager.initClassification(form.getIdCompetition());
			String viewStr = "redirect:/competitions/viewCalendar";
			modelAndView.setViewName(viewStr);
		}
		return modelAndView;
	}

	@RequestMapping ("/update")
	public ModelAndView update(@ModelAttribute("competition_session") Competition competition) {
		ModelAndView modelAndView = new ModelAndView("competitions_update");
		CompetitionsForm competitionsForm = competitionsManager.queryCompetitionsById(competition.getId());
		modelAndView.addObject("my_form", competitionsForm);
		boolean competitionsHasMatches = matchesManager.hasMatches(competition.getId());
		modelAndView.addObject("is_updatable", !competitionsHasMatches);
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
				String viewNameStr = "redirect:/competitions/viewCalendar?update_done=true";
				modelAndView.setViewName(viewNameStr);
				Competition competition = competitionsManager.queryCompetitionsByIdEntity(competitionsForm.getId());
				modelAndView.addObject("competition_session", competition);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return modelAndView;
	}

	@RequestMapping("/publishCalendar")
	public String publishCalendar(@ModelAttribute("competition_session") Competition competition) throws Exception {
		Long id = competition.getId();
		String redirectTo;
		if (matchesManager.checkUpdatesToPublish(id)) {
			List<Ref<Team>> teamsAffectedByChanges =  matchesManager.teamsAffectedByChanges(id);
			matchesManager.updatePublishedMatches(id);
			classificationManager.updateClassificationByCompetition(id);
			competition.setLastPublished(new Date());
			competition.setTeamsAffectedByPublish(teamsAffectedByChanges);
			competitionsManager.update(competition);
            redirectTo = "publish_done=true";
            String fcmKeyServer = parametersManager.getParameterFcmKeyServer();
            long code = LocalSportsUtils.sendNotificationToFirebase(competition, fcmKeyServer);
			if (code==-1) {
			    redirectTo += "&notification_error=true";
            }
		} else {
			redirectTo = "publish_none=true";
		}
		return "redirect:/competitions/viewCalendar?" + redirectTo;
	}

	@RequestMapping ("/addSanction")
	public ModelAndView addSanction(@ModelAttribute("competition_session") Competition competition) {
		ModelAndView modelAndView = new ModelAndView("sanction_add");
		SanctionForm sanctionForm = new SanctionForm();
		sanctionForm.setIdCompetition(competition.getId());
		modelAndView.addObject("my_form", sanctionForm);
		return modelAndView;
	}

	@RequestMapping("/doAddSanction")
	public ModelAndView doAddSanction(@ModelAttribute("my_form") SanctionForm sanctionForm, BindingResult bindingResult) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		sanctionFormValidator.validate(sanctionForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("sanction_add");
		} else {
			sanctionsManager.add(sanctionForm);
			modelAndView.setViewName("redirect:/competitions/viewClassification");
		}
		return modelAndView;
	}

	@RequestMapping ("/updateClassification")
	public ModelAndView updateClassification(@ModelAttribute("competition_session") Competition competition) {
		ModelAndView modelAndView = new ModelAndView("redirect:/competitions/viewClassification");
		classificationManager.updateClassificationByCompetition(competition.getId());
		return modelAndView;
	}

}
