package com.adiaz.controllers;

import com.adiaz.entities.*;
import com.adiaz.forms.*;
import com.adiaz.forms.validators.CompetitionInitFormValidator;
import com.adiaz.services.*;
import com.adiaz.utils.LocalSportsConstants;
import com.adiaz.utils.LocalSportsUtils;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by toni on 09/09/2017.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = Logger.getLogger(AdminController.class);
	@Autowired
	CourtManager courtManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	@Autowired SportsManager sportsManager;
	@Autowired CategoriesManager categoriesManager;
	@Autowired TeamManager teamManager;
	@Autowired CompetitionsManager competitionsManager;
	@Autowired CompetitionInitFormValidator competitionInitFormValidator;


	@RequestMapping("/initCompetition")
	public ModelAndView competition(
			@RequestParam (required = false, defaultValue = "false") boolean competitionCreated,
			@RequestParam (required = false, defaultValue = "false") boolean errorCourts){
		ModelAndView modelAndView = new ModelAndView("init_competition");
		modelAndView.addObject("my_form", new CompetitionsForm());
		modelAndView.addObject("error_courts", errorCourts);
		modelAndView.addObject("competition_created", competitionCreated);
		return modelAndView;
	}

	@RequestMapping("/doInitCompetition")
	public ModelAndView doInit(@ModelAttribute("my_form") CompetitionsForm competitionsForm, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		this.competitionInitFormValidator.validate(competitionsForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", competitionsForm);
			modelAndView.setViewName("init_competition");
		} else {
			try {
				List<Court> courts = courtManager.querySportCourtsByTownAndSport(
						competitionsForm.getIdTown(), competitionsForm.getIdSport());
				boolean errorCourts = courts.size()<=0;
				boolean competitionCreated = false;
				if (!errorCourts) {
					createCompetition(competitionsForm, Ref.create(courts.get(0)));
					competitionCreated = true;
				}
				String addressTarget = "redirect:/admin/initCompetition";
				addressTarget += "?errorCourts=" + errorCourts;
				addressTarget += "&competitionCreated=" + competitionCreated;
				modelAndView.setViewName(addressTarget);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return modelAndView;
	}

	private void createCompetition(CompetitionsForm f, Ref<Court> refCourt) throws Exception {
		Set<String> teamsSet = LocalSportsUtils.parseCalendarGetTeams();
		List<String> teamsList = new ArrayList<>(teamsSet);
		createTeams(teamsList, f.getIdTown(), f.getIdCategory(), f.getIdSport());
		Long idCompetition = createCompetitionEntity(f.getName(), f.getIdTown(), f.getIdCategory(), f.getIdSport());
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		List<MatchForm> matchesList = LocalSportsUtils.parseCalendarGetMatches();
		createMatches(matchesList, competition, refCourt);
		createClassification(competition);
	}

	private void createClassification(Competition competition) throws Exception {
		classificationManager.initClassification(competition.getId());
		try {
			classificationManager.updateClassificationByCompetition(competition.getId());
		} catch (Exception e) {
			logger.error("error on create classification ", e);
		}
		competition.setLastPublished(new Date());
		competitionsManager.update(competition);
	}

	private void createMatches(List<MatchForm> matchesFormList, Competition competition, Ref<Court> refCourt) throws Exception {
		Ref<Competition> competitionRef = Ref.create(competition);
		Map<String, Ref<Team>> teamsMap = new HashMap<>();
		for (Ref<Team> teamRef : competition.getTeams()) {
			teamsMap.put(teamRef.get().getName(), teamRef);
		}
		List<Match> matchesList = new ArrayList<>();
		for (MatchForm matchForm : matchesFormList) {
			Ref<Team> teamRefLocal = teamsMap.get(matchForm.getTeamLocalName());
			Ref<Team> teamRefVisitor = teamsMap.get(matchForm.getTeamVisitorName());
			Match match = new Match();
			match.setScoreLocal(matchForm.getScoreLocal());
			match.setScoreVisitor(matchForm.getScoreVisitor());
			match.setState(LocalSportsConstants.MATCH_STATE_PENDING);
			match.setTeamLocalRef(teamRefLocal);
			match.setTeamVisitorRef(teamRefVisitor);
			match.setDate(LocalSportsUtils.parseStringToDate(matchForm.getDateStr()));
			match.setCourtRef(refCourt);
			match.setCompetitionRef(competitionRef);
			match.setWeek(matchForm.getWeek());
			matchesList.add(match);
		}
		matchesList.get(0).setState(LocalSportsConstants.MATCH_STATE_PLAYED);
		matchesList.get(0).setScoreLocal(1);
		matchesList.get(0).setScoreVisitor(1);
		matchesList.get(1).setState(LocalSportsConstants.MATCH_STATE_PLAYED);
		matchesList.get(1).setScoreLocal(2);
		matchesList.get(1).setScoreVisitor(3);
		matchesList.get(2).setState(LocalSportsConstants.MATCH_STATE_PLAYED);
		matchesList.get(2).setScoreLocal(2);
		matchesList.get(2).setScoreVisitor(1);
		matchesManager.addMatchListAndPublish(matchesList);
	}

	private Long createCompetitionEntity(String name, Long townIdLega, Long idCategory, Long idSport) throws Exception {
		TeamFilterForm teamFilterForm = new TeamFilterForm();
		teamFilterForm.setIdCategory(idCategory);
		teamFilterForm.setIdSport(idSport);
		teamFilterForm.setIdTown(townIdLega);
		List<Team> teams = teamManager.queryByFilter(teamFilterForm);
		Long teamsArray[] = new Long[teams.size()];
		for (int i=0; i<teams.size(); i++) {
			teamsArray[i] = teams.get(i).getId();
		}
		CompetitionsForm competitionsForm = new CompetitionsForm();
		competitionsForm.setName(name);
		competitionsForm.setIdSport(idSport);
		competitionsForm.setIdCategory(idCategory);
		competitionsForm.setIdTown(townIdLega);
		competitionsForm.setTeams(teamsArray);
		Long idCompetition = competitionsManager.add(competitionsForm);
		return idCompetition;
	}

	private void createTeams(List<String> teamsList, Long idTown, long idCategory, long idSport) throws Exception {
		List<Team> teams = new LinkedList<>();
		for (String teamName : teamsList) {
			TeamForm teamForm = new TeamForm();
			teamForm.setName(teamName);
			teamForm.setIdTown(idTown);
			teamForm.setIdCategory(idCategory);
			teamForm.setIdSport(idSport);
			teams.add(teamForm.formToEntity());
		}
		teamManager.add(teams);
	}
}
