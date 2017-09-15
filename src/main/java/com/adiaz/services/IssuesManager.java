package com.adiaz.services;

import com.adiaz.entities.Issue;
import com.adiaz.forms.IssuesForm;

import java.util.List;

/**
 * Created by toni on 14/09/2017.
 */
public interface IssuesManager {

	List<Issue> queryIssues();
	List<Issue> queryIssuesByTown(Long idTown);
	List<Issue> queryIssuesByCompetition(Long idCompetition);
	Issue queryIssueById(Long idIssue);
	Long addIssue(IssuesForm issuesForm) throws Exception;
	boolean markIssueAsRead(Long idIssue, boolean newReadState) throws Exception;
	boolean reachMaxIssuesPerDay();
	boolean allowUserToReportIssue(String clientId);

	boolean isValidIssue(IssuesForm issuesForm);
}
