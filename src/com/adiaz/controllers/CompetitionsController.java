package com.adiaz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.entities.ClassificationEntryVO;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.formularies.CompetitionsForm;
import com.adiaz.formularies.LoadMatchesForm;
import com.adiaz.services.ClassificationManager;
import com.adiaz.services.CompetitionsManager;
import com.adiaz.services.MatchesManager;
import com.adiaz.utils.UtilsLegaSport;


@Controller
@RequestMapping ("/competitions")
public class CompetitionsController {

	@Autowired CompetitionsManager competitionsManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	
	@RequestMapping ("/list")
	public ModelAndView getCompetitions() {
		ModelAndView modelAndView = new ModelAndView("competitions_list");
		modelAndView.addObject("competitions_list", competitionsManager.queryCompetitions());
		return modelAndView;
	}
	
	@RequestMapping ("/add")
	public ModelAndView addCompetitions() {
		ModelAndView modelAndView = new ModelAndView("competitions_add");
		modelAndView.addObject("my_form", new CompetitionsForm());
		return modelAndView;
	}
	
	@RequestMapping ("/doAdd")
	public String doAddCompetition(@ModelAttribute("my_form") CompetitionsForm competitionsForm) {
		try {
			competitionsManager.add(competitionsForm.getName(), competitionsForm.getSportId(), competitionsForm.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/competitions/list";
	}
	
	@RequestMapping("/doRemove")
	public String doAddCompetition(@RequestParam(value = "idCompetition") Long idCompetition) {
		CompetitionsVO competition = competitionsManager.queryCompetitionsById(idCompetition);
		try {
			competitionsManager.remove(competition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/competitions/list";
	}
	
	@RequestMapping ("/viewCalendar")
	public ModelAndView viewCalendar(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_calendar");
		CompetitionsVO competitionsById = competitionsManager.queryCompetitionsById(idCompetition);
		List<MatchesVO> matchesList = matchesManager.queryMatchesByCompetition(idCompetition);
		modelAndView.addObject("competition", competitionsById);
		modelAndView.addObject("matches_list", matchesList);
		return modelAndView;
	}
	
	@RequestMapping ("/viewClassification")
	public ModelAndView viewClassification(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_classification");
		CompetitionsVO competition = competitionsManager.queryCompetitionsById(idCompetition);
		List<ClassificationEntryVO> classificationList = classificationManager.queryClassificationBySport(idCompetition);
		modelAndView.addObject("competition", competition);
		modelAndView.addObject("classification_list", classificationList);
		return modelAndView;
	}	
	
	@RequestMapping ("/loadClassification")
	public ModelAndView loadClassification (@RequestParam(value="idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_load_classification");
		CompetitionsVO competitionsById = competitionsManager.queryCompetitionsById(idCompetition);
		modelAndView.addObject("competition", competitionsById);
		LoadMatchesForm loadMatchesForm = new LoadMatchesForm();
		loadMatchesForm.setIdCompetition(idCompetition);
		modelAndView.addObject("my_form", loadMatchesForm);
		return modelAndView;
	}
	
	@RequestMapping ("/doLoadClassification")
	public String doLoadClassification(@ModelAttribute("my_form") LoadMatchesForm loadMatchesForm) {
		List<ClassificationEntryVO> classificationList = 
				UtilsLegaSport.parseClassification(loadMatchesForm.getMatchesTxt(), loadMatchesForm.getIdCompetition());
		try {
			classificationManager.add(classificationList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "redirect:/competitions/viewClassification?idCompetition=" + loadMatchesForm.getIdCompetition();
	}	
	
	@RequestMapping ("/loadCalendar")
	public ModelAndView loadCalendar(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_load_calendar");
		CompetitionsVO competitionsById = competitionsManager.queryCompetitionsById(idCompetition);
		modelAndView.addObject("competition", competitionsById);
		LoadMatchesForm loadMatchesForm = new LoadMatchesForm();
		loadMatchesForm.setIdCompetition(idCompetition);
		modelAndView.addObject("my_form", loadMatchesForm);
		return modelAndView;
	}
	
	@RequestMapping ("/doLoadCalendar")
	public String doLoadCalendar(@ModelAttribute("my_form") LoadMatchesForm loadMatchesForm) {
		CompetitionsVO competitionsById = competitionsManager.queryCompetitionsById(loadMatchesForm.getIdCompetition());
		List<MatchesVO> matchesList = UtilsLegaSport.parseCalendar(loadMatchesForm.getMatchesTxt(), competitionsById);
		try {
			matchesManager.add(matchesList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "redirect:/competitions/viewCalendar?idCompetition=" + loadMatchesForm.getIdCompetition();
	}
	
	
}
