package com.adiaz.controllers;

import com.adiaz.forms.CompetitionsInitForm;
import com.adiaz.forms.validators.CompetitionInitFormValidator;
import com.adiaz.services.*;
import com.adiaz.utils.LocalSportsConstants;
import com.adiaz.utils.initcompetition.InitCompetitionResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
	public ModelAndView initCompetition() {
		ModelAndView modelAndView = new ModelAndView("init_competition");
		modelAndView.addObject("my_form", new CompetitionsInitForm());
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
                InitCompetitionResult initCompetitionResult;
                try {
                    initCompetitionResult = competitionsInitManager.initCompetition(competitionsInitForm);
                    if (initCompetitionResult.getCompetition()!=null) {
                        modelAndView = new ModelAndView("init_competition");
                        modelAndView.addObject("my_form", new CompetitionsInitForm());
                        modelAndView.addObject("id_competition", initCompetitionResult.getCompetition().getName());
                        modelAndView.addObject("competition_created", competitionCreated);
                    } else {
                        String inputTxt = competitionsInitForm.getMatchesTxt();
                        String msgError = initCompetitionResult.getErrorDesc();
                        FieldError fieldError = new FieldError("my_form", "matchesTxt", inputTxt, false, null, null, msgError);
                        //bindingResult.addError(new FieldError ("my_form", "matchesTxt", initCompetitionResult.getErrorDesc()));
                        bindingResult.addError(fieldError);
                        modelAndView.addObject("my_form", competitionsInitForm);
                        modelAndView.setViewName("init_competition");
                    }
                } catch (Exception e) {
                    logger.error("Creating competition error: " + e.getMessage(), e);
                }
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return modelAndView;
	}

}
