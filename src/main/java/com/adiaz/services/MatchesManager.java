package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Match;
import com.adiaz.entities.Team;
import com.adiaz.forms.GenerateCalendarForm;
import com.adiaz.forms.MatchForm;
import com.googlecode.objectify.Ref;

public interface MatchesManager {

	public Long add(Match item) throws Exception;
	public Long addPublishedAndWorkingcopy(Match match) throws Exception;
	public boolean remove(Match item) throws Exception;
	public void removeAll() throws Exception;	
	public boolean update(MatchForm matchForm) throws Exception;
	public boolean update(Match match) throws Exception;
	public void updatePublishedMatches(Long idCompetition) throws Exception;
	public boolean checkUpdatesToPublish(Long idCompetition) throws Exception;
	public List<Match> queryMatchesByCompetitionWorkingCopy(Long competitionId);
	public List<Match> queryMatchesByCompetitionPublished(Long competitionId);
	public List<MatchForm> queryMatchesFormWorkingCopy(Long competitionId);
	public void addMatchListAndPublish(List<Match> matchesList) throws Exception;
	public List<Match> queryMatches();
	public Match queryMatchesById(Long id);
	public Integer howManyWeek(List<MatchForm> matchFormList);
	public void generateCalendar(GenerateCalendarForm form) throws Exception;
	public List<Ref<Team>> teamsAffectedByChanges(Long idCompetition);
	public boolean hasMatches(Long idCompetition);
}
