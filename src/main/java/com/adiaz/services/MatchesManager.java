package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Match;
import com.adiaz.entities.Team;
import com.adiaz.forms.GenerateCalendarForm;
import com.adiaz.forms.MatchForm;
import com.googlecode.objectify.Ref;

public interface MatchesManager {

	Long add(Match item) throws Exception;
	Long addPublishedAndWorkingcopy(Match match) throws Exception;
	boolean remove(Match item) throws Exception;
	void removeAll() throws Exception;
	void removeByCompetition(Long id);
	boolean update(MatchForm matchForm) throws Exception;
	boolean update(Match match) throws Exception;
	void updatePublishedMatches(Long idCompetition) throws Exception;
	boolean checkUpdatesToPublish(Long idCompetition) throws Exception;
	List<Match> queryMatchesByCompetitionWorkingCopy(Long competitionId);
	List<Match> queryMatchesByCompetitionPublished(Long competitionId);
	List<MatchForm> queryMatchesFormWorkingCopy(Long competitionId);
    List<List<Match>> queryMatchesOnWeeks(Long idCompetition);
	void addMatchListAndPublish(List<Match> matchesList) throws Exception;
	List<Match> queryMatches();
	Match queryMatchesById(Long id);
	Integer howManyWeek(List<MatchForm> matchFormList);
	void generateCalendar(GenerateCalendarForm form) throws Exception;
	List<Ref<Team>> teamsAffectedByChanges(Long idCompetition);
	boolean hasMatches(Long idCompetition);
	List<String> initWeeksNames(long idCompetition);
}
