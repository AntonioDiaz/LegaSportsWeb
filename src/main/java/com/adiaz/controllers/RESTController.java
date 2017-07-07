package com.adiaz.controllers;

import java.util.List;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adiaz.entities.CategoriesVO;
import com.adiaz.entities.ClassificationEntryVO;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.entities.SportVO;
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
	public ResponseEntity<SportVO> getSportsById(@PathVariable("id") long id) {
		ResponseEntity<SportVO> response;
		SportVO sport = sportsManager.querySportsById(id);
		if (sport==null) {
			response = new ResponseEntity<SportVO>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<SportVO>(sport, HttpStatus.OK);
		}
		return response;
	}
	
	@RequestMapping(value = "/sports_name/{name}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SportVO> getSportsById(@PathVariable("name") String name) {
		ResponseEntity<SportVO> response;
		SportVO sport = sportsManager.querySportsByName(name);
		if (sport==null) {
			response = new ResponseEntity<SportVO>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<SportVO>(sport, HttpStatus.OK);
		}
		return response;
	}
	
//	@RequestMapping(value = "/categories/{sport_id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<CategoriesVO> getCategoriesBySportsId(@PathVariable("sport_id") Long sportId) {
//		SportVO sportVO = sportsManager.querySportsById(sportId);
//		List<CategoriesVO> categoriesBySportId = categoriesManager.queryCategoriesBySport(sportVO);
//		return categoriesBySportId;
//	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CategoriesVO> getCategories() {	
		List<CategoriesVO> queryCategories = categoriesManager.queryCategories();
		return queryCategories;
	}
	
	@RequestMapping(value="/competitions", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompetitionsVO> competitions() {
		logger.debug("*competitions");
		List<CompetitionsVO> competitions = competitionsManager.queryCompetitions(); 
		for (CompetitionsVO competitionsVO : competitions) {
			List<MatchesVO> matchesList = matchesManager.queryMatchesByCompetition(competitionsVO.getId());			
			competitionsVO.setMatches(matchesList);
			List<ClassificationEntryVO> classification = classificationManager.queryClassificationBySport(competitionsVO.getId());
			competitionsVO.setClassification(classification);
		}
		return competitions;
	}
	
	@RequestMapping(value="/search_competitions", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompetitionsVO> searchCompetitions(
			@RequestParam(value="idSport", required=false) Long idSport, 
			@RequestParam(value="idCategory", required=false) Long idCategory) {
		List<CompetitionsVO> competitions = competitionsManager.queryCompetitions(idSport, idCategory); 
		return competitions;
	}	
	
	@RequestMapping(value="/matches", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MatchesVO> getMatches() {
		List<MatchesVO> matches = matchesManager.queryMatches();
		return matches;
	}	
	
	@RequestMapping(value="/matches/{competition_id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MatchesVO> getMatches(@PathVariable("competition_id") Long competitionId) {
		List<MatchesVO> matches = matchesManager.queryMatchesByCompetition(competitionId);		
		return matches;
	}
	
	@RequestMapping(value="/sports",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SportVO> listSports() {
		List<SportVO> sportsList = sportsManager.querySports(); 
		return sportsList;
	}
}