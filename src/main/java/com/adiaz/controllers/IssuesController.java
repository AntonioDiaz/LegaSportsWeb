package com.adiaz.controllers;

import com.adiaz.entities.Issue;
import com.adiaz.forms.CompetitionsFilterForm;
import com.adiaz.forms.IssuesForm;
import com.adiaz.services.IssuesManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by toni on 15/09/2017.
 */
@Controller
@RequestMapping("/issues")
public class IssuesController {

	private static final Logger logger = Logger.getLogger(IssuesController.class);


	@Autowired
	IssuesManager issuesManager;

	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView("query_issues");
		modelAndView.addObject("form_filter", new IssuesForm());
		return modelAndView;
	}

	@RequestMapping("/doFilter")
	public ModelAndView doFilter(@ModelAttribute("form_filter")IssuesForm issuesForm){
		ModelAndView modelAndView = new ModelAndView("query_issues");
		List<Issue> issues;
		if (issuesForm.getCompetitionId()!=null) {
			issues = issuesManager.queryIssuesByCompetition(issuesForm.getCompetitionId());
		} else if (issuesForm.getTownId()!=null) {
			issues = issuesManager.queryIssuesByTown(issuesForm.getTownId());
		} else {
			issues = issuesManager.queryIssues();
		}
		modelAndView.addObject("issues", issues);
		return modelAndView;
	}

}
