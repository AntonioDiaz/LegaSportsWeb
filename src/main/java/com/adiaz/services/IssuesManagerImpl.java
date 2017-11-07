package com.adiaz.services;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.IssuesDAO;
import com.adiaz.entities.Issue;
import com.adiaz.entities.Match;
import com.adiaz.forms.IssuesForm;
import com.adiaz.utils.LocalSportsConstants;
import com.adiaz.utils.LocalSportsUtils;
import com.googlecode.objectify.Key;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by toni on 14/09/2017.
 */

@Service("issuesManager")
public class IssuesManagerImpl implements IssuesManager {

	@Autowired
	IssuesDAO issuesDAO;

	@Autowired
	CompetitionsDAO competitionsDAO;

	@Override
	public List<Issue> queryIssues() {
		return issuesDAO.findAll();
	}

	@Override
	public List<Issue> queryIssuesByTown(Long idTown) {
		return issuesDAO.findByTown(idTown);
	}

	@Override
	public List<Issue> queryIssuesByCompetition(Long idCompetition) {
		return issuesDAO.findByCompetition(idCompetition);
	}

	@Override
	public Issue queryIssueById(Long idIssue) {
		return issuesDAO.findById(idIssue);
	}

	@Override
	public Long addIssue(IssuesForm issuesForm) throws Exception {
		Issue issue = issuesForm.formToEntity();
		issue.getRefs();
		if (issue.getMatchRef()!=null) {
			Match match = issue.getMatchRef().get();
			issue.setMatchDescription(match.getFullDescription());
		}
		Key<Issue> issueKey = issuesDAO.create(issue);
		return issueKey.getId();
	}

	@Override
	public boolean markIssueAsRead(Long idIssue, boolean newReadState) throws Exception {
		Issue issue = issuesDAO.findById(idIssue);
		issue.setRead(newReadState);
		return issuesDAO.update(issue);
	}

	/**
	 * Calculate is have been reached the maximun issues per day, to avoid DDOS attack.
	 * @return
	 */
	@Override
	public boolean reachMaxIssuesPerDay() {
		Date dateFrom = LocalSportsUtils.calculateLastMidnight();
		Date dateTo = LocalSportsUtils.calculateNextMidnigth();
		List<Issue> issues = issuesDAO.findInPeriod(dateFrom, dateTo);
		return issues.size()>= LocalSportsConstants.MAX_ISSUES_PER_DAY;
	}

	/**
	 * Check if an client cant report more issues, this is to avoid DDOS attack.
	 * Each client can sent only 5 issues per day.
	 * @param clientId
	 * @return
	 */
	@Override
	public boolean allowUserToReportIssue(String clientId) {
		Date dateFrom = LocalSportsUtils.calculateLastMidnight();
		Date dateTo = LocalSportsUtils.calculateNextMidnigth();
		List<Issue> issues = issuesDAO.findByClientIdInPeriod(clientId, dateFrom, dateTo);
		return issues.size()< LocalSportsConstants.MAX_ISSUES_PER_CLIENT_AND_DAY;
	}

	@Override
	public boolean isValidIssue(IssuesForm issuesForm) {
		return
				issuesForm.getCompetitionId()!=null
						&& StringUtils.isNotBlank(issuesForm.getClientId())
						&& issuesForm.getMatchId()!=null
					&& StringUtils.isNotBlank(issuesForm.getDescription());
	}
}
