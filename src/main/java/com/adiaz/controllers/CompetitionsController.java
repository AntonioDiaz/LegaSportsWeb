package com.adiaz.controllers;

import com.adiaz.entities.ClassificationEntry;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Match;
import com.adiaz.forms.CompetitionsFilterForm;
import com.adiaz.forms.CompetitionsForm;
import com.adiaz.forms.validators.CompetitionsFormValidator;
import com.adiaz.forms.LoadMatchesForm;
import com.adiaz.services.ClassificationManager;
import com.adiaz.services.CompetitionsManager;
import com.adiaz.services.MatchesManager;
import com.adiaz.services.SportCenterCourtManager;
import com.adiaz.utils.UtilsLegaSport;
import static com.adiaz.utils.UtilsLegaSport.getActiveUser;
import org.apache.log4j.Logger;
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
@RequestMapping ("/competitions")
@SessionAttributes ("form_filter")
public class CompetitionsController {
	private static final Logger logger = Logger.getLogger(CompetitionsController.class);
	@Autowired CompetitionsManager competitionsManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	@Autowired CompetitionsFormValidator competitionsFormValidator;
	@Autowired
	SportCenterCourtManager sportCenterCourtManager;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="add_done", defaultValue="false") boolean addDone){
		ModelAndView modelAndView = new ModelAndView("competitions_list");
		modelAndView.addObject("form_filter", new CompetitionsFilterForm());
		modelAndView.addObject("add_done", addDone);
		return modelAndView;
	}

	@RequestMapping("/doFilter")
	public ModelAndView doFilter(
			@RequestParam(value="update_done", defaultValue="false") boolean updateDone,
			@RequestParam(value="remove_done", defaultValue="false") boolean removeDone,
			@ModelAttribute("form_filter") CompetitionsFilterForm competitionsFilterForm){
		ModelAndView modelAndView = new ModelAndView("competitions_list");
		modelAndView.addObject("form_filter", competitionsFilterForm);
		List<Competition> competitions = competitionsManager.queryCompetitions(competitionsFilterForm);
		modelAndView.addObject("competitions", competitions);
		modelAndView.addObject("remove_done", removeDone);
		modelAndView.addObject("update_done", updateDone);
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
		return "redirect:/competitions/doFilter?remove_done=true";
	}
	
	@RequestMapping ("/viewCalendar")
	public ModelAndView viewCalendar(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_calendar");
		Competition competitionsById = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		List<Match> matchesList = matchesManager.queryMatchesByCompetition(idCompetition);
		Integer howManyWeek = matchesManager.howManyWeek(matchesList);
		modelAndView.addObject("competition", competitionsById);
		modelAndView.addObject("matches_list", matchesList);
		modelAndView.addObject("weeks_count", howManyWeek);
		return modelAndView;
	}
	
	@RequestMapping ("/viewClassification")
	public ModelAndView viewClassification(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_classification");
		Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		List<ClassificationEntry> classificationList = classificationManager.queryClassificationBySport(idCompetition);
		modelAndView.addObject("competition", competition);
		modelAndView.addObject("classification_list", classificationList);
		return modelAndView;
	}	
	
	@RequestMapping ("/loadClassification")
	public ModelAndView loadClassification (@RequestParam(value="idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_load_classification");
		Competition competitionsById = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
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
		Competition competitionsById = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
		modelAndView.addObject("competition", competitionsById);
		LoadMatchesForm loadMatchesForm = new LoadMatchesForm();
		loadMatchesForm.setIdCompetition(idCompetition);
		modelAndView.addObject("my_form", loadMatchesForm);
		return modelAndView;
	}

	@RequestMapping ("/doLoadCalendar")
	public String doLoadCalendar(@ModelAttribute("my_form") LoadMatchesForm loadMatchesForm) {
		Competition competitionsById = competitionsManager.queryCompetitionsByIdEntity(loadMatchesForm.getIdCompetition());
		List<Match> matchesList = UtilsLegaSport.parseCalendar(loadMatchesForm.getMatchesTxt(), competitionsById, null);
		try {
			matchesManager.add(matchesList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "redirect:/competitions/viewCalendar?idCompetition=" + loadMatchesForm.getIdCompetition();
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
				competitionsManager.update(competitionsForm);
				modelAndView.setViewName("redirect:/competitions/doFilter?update_done=true");
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return modelAndView;
	}

	@RequestMapping ("/view")
	public ModelAndView view(@RequestParam(value = "idCompetition") Long idCompetition) {
		ModelAndView modelAndView = new ModelAndView("competitions_view");
		CompetitionsForm competitionsForm = competitionsManager.queryCompetitionsById(idCompetition);
		modelAndView.addObject("my_form", competitionsForm);
		return modelAndView;
	}
}
