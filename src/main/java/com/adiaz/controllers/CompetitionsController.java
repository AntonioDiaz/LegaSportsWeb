package com.adiaz.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.entities.ClassificationEntry;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.forms.CompetitionsForm;
import com.adiaz.forms.CompetitionsFormValidator;
import com.adiaz.forms.LoadMatchesForm;
import com.adiaz.services.ClassificationManager;
import com.adiaz.services.CompetitionsManager;
import com.adiaz.services.MatchesManager;
import com.adiaz.utils.UtilsLegaSport;


@Controller
@RequestMapping ("/competitions")
public class CompetitionsController {
	private static final Logger logger = Logger.getLogger(CompetitionsController.class);
	@Autowired CompetitionsManager competitionsManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	@Autowired CompetitionsFormValidator competitionsFormValidator;
	
	@RequestMapping ("/list")
	public ModelAndView listCompetitions(
			@RequestParam(value="update_done", defaultValue="false") boolean updateDone,
			@RequestParam(value="add_done", defaultValue="false") boolean addDone,
			@RequestParam(value="remove_done", defaultValue="false") boolean removeDone) {
		ModelAndView modelAndView = new ModelAndView("competitions_list");
		modelAndView.addObject("competitions_list", competitionsManager.queryCompetitions());
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
		modelAndView.addObject("add_done", addDone);
		return modelAndView;
	}
	
	@RequestMapping ("/add")
	public ModelAndView addCompetitions() {
		ModelAndView modelAndView = new ModelAndView("competitions_add");
		modelAndView.addObject("my_form", new CompetitionsForm());
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
				competitionsManager.add(competitionsForm.getName(), competitionsForm.getSportId(), competitionsForm.getCategoryId());
				modelAndView.setViewName("redirect:/competitions/list?add_done=true");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return modelAndView;
	}
	
	@RequestMapping("/doRemove")
	public String doRemoveCompetition(@RequestParam(value = "idCompetition") Long idCompetition) {
		CompetitionsVO competition = competitionsManager.queryCompetitionsById(idCompetition);
		try {
			competitionsManager.remove(competition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/competitions/list?remove_done=true";
	}
	
	@RequestMapping ("/viewCalendar")
	public ModelAndView viewCalendar(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_calendar");
		CompetitionsVO competitionsById = competitionsManager.queryCompetitionsById(idCompetition);
		List<MatchesVO> matchesList = matchesManager.queryMatchesByCompetition(idCompetition);
        Integer howManyWeek = matchesManager.howManyWeek(matchesList);
        modelAndView.addObject("competition", competitionsById);
		modelAndView.addObject("matches_list", matchesList);
		modelAndView.addObject("weeks_count", howManyWeek);
		return modelAndView;
	}
	
	@RequestMapping ("/viewClassification")
	public ModelAndView viewClassification(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_classification");
		CompetitionsVO competition = competitionsManager.queryCompetitionsById(idCompetition);
		List<ClassificationEntry> classificationList = classificationManager.queryClassificationBySport(idCompetition);
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
		List<ClassificationEntry> classificationList =
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
