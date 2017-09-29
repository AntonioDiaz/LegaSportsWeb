package com.adiaz.services;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.MatchesDAO;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Court;
import com.adiaz.entities.Match;
import com.adiaz.entities.Team;
import com.adiaz.forms.GenerateCalendarForm;
import com.adiaz.forms.MatchForm;
import com.adiaz.utils.LocalSportsConstants;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service ("matchesManager")
public class MatchesManagerImpl implements MatchesManager {

	private static final Logger logger = Logger.getLogger(MatchesManagerImpl.class.getName());

	@Autowired
	MatchesDAO matchesDAO;
	@Autowired
	CompetitionsDAO competitionsDAO;
	@Autowired
	CourtManager courtManager;

	@Override
	public Long add(Match match) throws Exception {
		initMatchStateAndScores(match);
		return matchesDAO.create(match).getId();
	}

	private void initMatchStateAndScores(Match match) {
		if (match.getState()==null) {
			match.setState(LocalSportsConstants.MATCH_STATE_PENDING);
		}
		if (match.getState()== LocalSportsConstants.MATCH_STATE_CANCELED
				|| match.getState()== LocalSportsConstants.MATCH_STATE_PENDING) {
			match.setScoreLocal(0);
			match.setScoreVisitor(0);
		}
	}

	@Override
	public Long addPublishedAndWorkingcopy(Match match) throws Exception {
		match.setWorkingCopy(false);
		Long idPublisedCopy = this.add(match);
		Key<Match> matchKey = Key.create(Match.class, idPublisedCopy);
		match.setId(null);
		match.setWorkingCopy(true);
		match.setMatchPublishedRef(Ref.create(matchKey));
		Long idWorkingCopy = this.add(match);
		return idWorkingCopy;
	}

	@Override
	public boolean remove(Match match) throws Exception {
		return matchesDAO.remove(match);
	}

	@Override
	public boolean update(MatchForm matchForm) throws Exception {
		Match match = queryMatchesById(matchForm.getId());
		match = matchForm.formToEntity(match);
		return update(match);
	}

	@Override
	public boolean update(Match match) throws Exception {
		initMatchStateAndScores(match);
		return matchesDAO.update(match);
	}

	/**
	 * Move all changes from the working copy to the published copy.
	 * @param idCompetition
	 * @throws Exception
	 */
	@Override
	public void updatePublishedMatches(Long idCompetition) throws Exception {
		List<Match> matches = queryMatchesByCompetitionWorkingCopy(idCompetition);
		for (Match match : matches) {
			// TODO: 22/07/2017 optimize this method, check if it is necessary to update.
			Match published = match.getMatchPublished();
			published.setScoreLocal(match.getScoreLocal());
			published.setScoreVisitor(match.getScoreVisitor());
			published.setDate(match.getDate());
			published.setCourtRef(match.getCourtRef());
			published.setTeamLocalRef(match.getTeamLocalRef());
			published.setTeamVisitorRef(match.getTeamVisitorRef());
			published.setState(match.getState());
			//// TODO: 22/07/2017 optimize this method, could be better to send a list of entities to update 
			matchesDAO.update(published);
		}
	}

	@Override
	public boolean checkUpdatesToPublish(Long idCompetition) throws Exception {
		List<MatchForm> matchesForm = queryMatchesFormWorkingCopy(idCompetition);
		for (MatchForm matchForm : matchesForm) {
			if (matchForm.checkIfChangesToPublish()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Match> queryMatchesByCompetitionWorkingCopy(Long competitionId) {
		return matchesDAO.findByCompetition(competitionId, true);
	}

	@Override
	public List<MatchForm> queryMatchesFormWorkingCopy(Long competitionId) {
		List <MatchForm> matchFormList = new ArrayList<>();
		List<Match> byCompetition = matchesDAO.findByCompetition(competitionId, true);
		for (Match match : byCompetition) {
			matchFormList.add(new MatchForm(match));
		}
		return matchFormList;
	}


	@Override
	public List<Match> queryMatchesByCompetitionPublished(Long competitionId) {
		return matchesDAO.findByCompetition(competitionId, false);
	}

	@Override
	public void addMatchListAndPublish(List<Match> matchesList) throws Exception {
		for (Match match : matchesList) {
			addPublishedAndWorkingcopy(match);
		}
	}

	@Override
	public List<Match> queryMatches() {
		return matchesDAO.findAll();
	}

	@Override
	public Match queryMatchesById(Long id) {
		return matchesDAO.findById(id);
	}

	@Override
    public Integer howManyWeek(List<MatchForm> matchFormList) {
        Set<Integer> diferentsWeeks = new HashSet<>();
		for (MatchForm matchForm : matchFormList) {
			diferentsWeeks.add(matchForm.getWeek());
        }
        return diferentsWeeks.size();
    }

    @Override
	public void removeAll() throws Exception {
		List<Match> queryAllMatches = matchesDAO.findAll();
		for (Match match : queryAllMatches) {
			matchesDAO.remove(match);
		}		
	}

	@Override
	public void generateCalendar(GenerateCalendarForm form) throws Exception {
		Competition competition = competitionsDAO.findById(form.getIdCompetition());
		Ref<Competition> competitionRef = Ref.create(competition);
		Court court = courtManager.querySportCourt(form.getIdCourt());
		Ref<Court> courtRef = Ref.create(court);
		int numTeams = competition.getTeams().size();
		int weeks = numTeams * 2 - 2;
		if (numTeams%2==1) {
			weeks = (numTeams +1) * 2 - 2;
		}
		int matchesEachWeek = (numTeams + 1) / 2;
		for (int i = 0; i < weeks; i++) {
			for (int j = 0; j < matchesEachWeek; j++) {
				Match match = new Match();
				match.setCompetitionRef(competitionRef);
				match.setCourtRef(courtRef);
				match.setWeek(i+1);
				match.setState(LocalSportsConstants.MATCH_STATE_PENDING);
				addPublishedAndWorkingcopy(match);
			}
		}
	}

	@Override
	public List<Ref<Team>> teamsAffectedByChanges(Long idCompetition) {
		Set<Ref<Team>> teamsSet = new HashSet<>();
		List<MatchForm> matchesForm = queryMatchesFormWorkingCopy(idCompetition);
		for (MatchForm matchForm : matchesForm) {
			if (matchForm.checkIfChangesToPublish()) {
				if (matchForm.getTeamLocalId()!=null) {
					Key<Team> teamLocalKey = Key.create(Team.class, matchForm.getTeamLocalId());
					teamsSet.add(Ref.create(teamLocalKey));
				}
				if (matchForm.getTeamVisitorId()!=null) {
					Key<Team> teamVisitorKey = Key.create(Team.class, matchForm.getTeamVisitorId());
					teamsSet.add(Ref.create(teamVisitorKey));
				}
			}
		}
		return new ArrayList<>(teamsSet);
	}
}
