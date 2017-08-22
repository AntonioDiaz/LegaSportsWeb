package com.adiaz.controllers;

import java.util.List;
import java.util.Map;

import com.adiaz.entities.*;
import com.adiaz.forms.MatchForm;
import com.adiaz.forms.TeamFilterForm;
import com.adiaz.forms.utils.MatchFormUtils;
import com.adiaz.services.*;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adiaz.entities.Sport;

@RestController
@RequestMapping("server")
public class RESTController {

	@Autowired
	SportsManager sportsManager;
	@Autowired
	CategoriesManager categoriesManager;
	@Autowired
	CompetitionsManager competitionsManager;
	@Autowired
	MatchesManager matchesManager;
	@Autowired
	ClassificationManager classificationManager;
	@Autowired
	SportCenterCourtManager sportCenterCourtManager;
	@Autowired
	TeamManager teamManager;
	@Autowired
	MatchFormUtils matchFormUtils;

	@Autowired
	TownManager townManager;

	private static final Logger logger = Logger.getLogger(RESTController.class);

	@RequestMapping(value = "/sports/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sport> getSportsById(@PathVariable("id") long id) {
		ResponseEntity<Sport> response;
		Sport sport = sportsManager.querySportsById(id);
		if (sport == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<>(sport, HttpStatus.OK);
		}
		return response;
	}

	@RequestMapping(value = "/sports_name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sport> getSportsById(@PathVariable("name") String name) {
		ResponseEntity<Sport> response;
		Sport sport = sportsManager.querySportsByName(name);
		if (sport == null) {
			response = new ResponseEntity<Sport>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Sport>(sport, HttpStatus.OK);
		}
		return response;
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Category> getCategories() {
		List<Category> queryCategories = categoriesManager.queryCategories();
		return queryCategories;
	}

	@RequestMapping(value = "/competitions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Competition> competitions() {
		List<Competition> competitions = competitionsManager.queryCompetitions();
		return competitions;
	}

	@RequestMapping(value = "/competitions/{competition_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Competition competitions(@PathVariable("competition_id") Long competitionId) {
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(competitionId);
		return competition;
	}

	@RequestMapping(value = "/search_competitions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Competition> searchCompetitions(
			@RequestParam(value = "idTown") Long idTown,
			@RequestParam(value = "onlyPublished", required = false, defaultValue = "true") Boolean onlyPublised) {
		List<Competition> competitions = competitionsManager.queryCompetitionsByTown(idTown, onlyPublised);
		return competitions;
	}

	@RequestMapping(value = "/matches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Match> getMatches() {
		List<Match> matches = matchesManager.queryMatches();
		return matches;
	}

	class CompetitionDetails {
		private List<Match> matches;
		private List<ClassificationEntry> classification;

		public List<Match> getMatches() {
			return matches;
		}

		public void setMatches(List<Match> matches) {
			this.matches = matches;
		}

		public List<ClassificationEntry> getClassification() {
			return classification;
		}

		public void setClassification(List<ClassificationEntry> classification) {
			this.classification = classification;
		}
	}

	@RequestMapping(value = "/competitiondetails/{competition_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CompetitionDetails getMatchesAndClassification(@PathVariable("competition_id") Long competitionId){
		List<Match> matches = matchesManager.queryMatchesByCompetitionPublished(competitionId);
		List<ClassificationEntry> classificationEntries = classificationManager.queryClassificationByCompetition(competitionId);
		CompetitionDetails competitionDetails = new CompetitionDetails();
		competitionDetails.matches = matches;
		competitionDetails.classification = classificationEntries;
		return competitionDetails;
	}

	@RequestMapping(value = "/matches/{competition_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Match> getMatches(@PathVariable("competition_id") Long competitionId) {
		List<Match> matches = matchesManager.queryMatchesByCompetitionPublished(competitionId);
		return matches;
	}

	@RequestMapping(value = "/classification/{competition_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ClassificationEntry> getClassification(@PathVariable("competition_id") Long competitionId) {
		List<ClassificationEntry> classificationList = classificationManager.queryClassificationByCompetition(competitionId);
		return classificationList;
	}

	@RequestMapping(value = "/sports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Sport> listSports() {
		List<Sport> sportsList = sportsManager.querySports();
		return sportsList;
	}

	@RequestMapping(value = "/match/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MatchForm> getMatch(@PathVariable("id") Long id) {
		Match match = matchesManager.queryMatchesById(id);
		if (match == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		MatchForm matchForm = matchFormUtils.entityToForm(match);
		return new ResponseEntity<>(matchForm, HttpStatus.OK);
	}

	// TODO: 10/07/2017 IMPORTAN protect this call in production environment. 
	@RequestMapping(value = "/match/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MatchForm> updateMatchScore(@PathVariable("id") Long id, @RequestBody MatchForm matchForm) {
		boolean updated;
		try {
			updated = matchesManager.update(matchForm);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		if (updated) {
			return new ResponseEntity<>(matchForm, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}

	@RequestMapping(value = "/courts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SportCenterCourt> courts(
			@RequestParam(value = "idTown") Long idTown,
			@RequestParam(value = "idSport") Long idSport) {
		return sportCenterCourtManager.querySportCourtsByTownAndSport(idTown, idSport);
	}

	@RequestMapping(value = "/teams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Team> teams(
			@RequestParam(value = "idTown") Long idTown,
			@RequestParam(value = "idSport") Long idSport,
			@RequestParam(value = "idCategory") Long idCategory) {
		TeamFilterForm teamFilterForm = new TeamFilterForm(idTown, idSport, idCategory);
		return teamManager.queryByFilter(teamFilterForm);
	}

	@RequestMapping(value = "/towns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Town> towns(){
		return townManager.queryActives();
	}

}