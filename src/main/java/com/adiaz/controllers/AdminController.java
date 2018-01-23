package com.adiaz.controllers;

import com.adiaz.forms.CompetitionsInitForm;
import com.adiaz.forms.validators.CompetitionInitFormValidator;
import com.adiaz.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by toni on 09/09/2017.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = Logger.getLogger(AdminController.class);
	@Autowired CompetitionInitFormValidator competitionInitFormValidator;
	@Autowired CompetitionsInitManager competitionsInitManager;


    @RequestMapping("/initCompetition")
	public ModelAndView competition(
			@RequestParam (required = false, defaultValue = "false") boolean competitionCreated,
			@RequestParam (required = false) Long idCompetition){
		ModelAndView modelAndView = new ModelAndView("init_competition");
		modelAndView.addObject("my_form", new CompetitionsInitForm());
		modelAndView.addObject("id_competition", idCompetition);
		modelAndView.addObject("competition_created", competitionCreated);
		return modelAndView;
	}

	@RequestMapping("/doInitCompetition")
	public ModelAndView doInit(@ModelAttribute("my_form") CompetitionsInitForm competitionsInitForm, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		this.competitionInitFormValidator.validate(competitionsInitForm, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("my_form", competitionsInitForm);
			modelAndView.setViewName("init_competition");
		} else {
			try {
				boolean competitionCreated = true;
				Long idCompetition = null;
                if (!competitionsInitManager.validaCompetitionInput(competitionsInitForm)) {
                    bindingResult.rejectValue("matchesTxt", "input_format_error");
                    modelAndView.addObject("my_form", competitionsInitForm);
                    modelAndView.setViewName("init_competition");
                } else {
                    try {
                        idCompetition = competitionsInitManager.initCompetition(competitionsInitForm);
                    } catch (Exception e) {
                        logger.error("Creating competition error: " + e.getMessage(), e);
                    }
                    String addressTarget = "redirect:/admin/initCompetition";
                    addressTarget += "?competitionCreated=" + competitionCreated;
                    addressTarget += "&idCompetition=" + idCompetition;
                    modelAndView.setViewName(addressTarget);
                }
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return modelAndView;
	}
}
