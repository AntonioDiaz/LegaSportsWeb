package com.adiaz.controllers;

import java.util.List;

import com.adiaz.entities.*;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.adiaz.entities.Sport;
import com.adiaz.services.CategoriesManager;
import com.adiaz.services.ClassificationManager;
import com.adiaz.services.CompetitionsManager;
import com.adiaz.services.MatchesManager;
import com.adiaz.services.SportsManager;

@RestController
@RequestMapping("server")
public class RESTController {

	@Autowired SportsManager sportsManager;
	@Autowired CategoriesManager categoriesManager;
	@Autowired CompetitionsManager competitionsManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	
	private static final Logger logger = Logger.getLogger(RESTController.class);
		
	@RequestMapping(value = "/sports/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sport> getSportsById(@PathVariable("id") long id) {
		ResponseEntity<Sport> response;
		Sport sport = sportsManager.querySportsById(id);
		if (sport==null) {
			response = new ResponseEntity<Sport>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Sport>(sport, HttpStatus.OK);
		}
		return response;
	}
	
	@RequestMapping(value = "/sports_name/{name}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sport> getSportsById(@PathVariable("name") String name) {
		ResponseEntity<Sport> response;
		Sport sport = sportsManager.querySportsByName(name);
		if (sport==null) {
			response = new ResponseEntity<Sport>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Sport>(sport, HttpStatus.OK);
		}
		return response;
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Category> getCategories() {
		List<Category> queryCategories = categoriesManager.queryCategories();
		return queryCategories;
	}
	
	@RequestMapping(value="/competitions", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Competition> competitions() {
		logger.debug("*competitions");
		List<Competition> competitions = competitionsManager.queryCompetitions();
		for (Competition competition : competitions) {
			List<Match> matchesList = matchesManager.queryMatchesByCompetition(competition.getId());
			competition.setMatches(matchesList);
			List<ClassificationEntry> classification = classificationManager.queryClassificationBySport(competition.getId());
			competition.setClassification(classification);
		}
		return competitions;
	}
	
	@RequestMapping(value="/search_competitions", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Competition> searchCompetitions(
			@RequestParam(value="idSport", required=false) Long idSport, 
			@RequestParam(value="idCategory", required=false) Long idCategory) {
		List<Competition> competitions = competitionsManager.queryCompetitions(idSport, idCategory);
		return competitions;
	}	
	
	@RequestMapping(value="/matches", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Match> getMatches() {
		List<Match> matches = matchesManager.queryMatches();
		return matches;
	}	
	
	@RequestMapping(value="/matches/{competition_id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Match> getMatches(@PathVariable("competition_id") Long competitionId) {
		List<Match> matches = matchesManager.queryMatchesByCompetition(competitionId);
		return matches;
	}
	
	@RequestMapping(value="/sports",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Sport> listSports() {
		List<Sport> sportsList = sportsManager.querySports();
		return sportsList;
	}

    @RequestMapping (value = "/match/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Match> getMatch(@PathVariable("id") Long id) {
        Match match = matchesManager.queryMatchesById(id);
        if (match ==null) {
            return new ResponseEntity<Match>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Match>(match, HttpStatus.OK);
    }

	// TODO: 10/07/2017 IMPORTAN protect this call in production environment. 
	@RequestMapping (value = "/match/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Match> updateMatchScore(@PathVariable("id") Long id, @RequestBody Match newMatchVO){
        Match match = matchesManager.queryMatchesById(id);
        if (match==null) {
            return new ResponseEntity<Match>(HttpStatus.NOT_FOUND);
        }
        match.setScoreLocal(newMatchVO.getScoreLocal());
        match.setScoreVisitor(newMatchVO.getScoreVisitor());
        try {
            boolean update = matchesManager.update(match);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<Match>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<Match>(match, HttpStatus.OK);
	}

}