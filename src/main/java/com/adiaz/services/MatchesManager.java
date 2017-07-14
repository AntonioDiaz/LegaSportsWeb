package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Match;

public interface MatchesManager {

	public void add(Match item) throws Exception;
	public boolean remove(Match item) throws Exception;
	public void removeAll() throws Exception;	
	public boolean update(Match item) throws Exception;
	public List<Match> queryMatchesByCompetition(Long competitionId);
	public void add(List<Match> matchesList) throws Exception;
	public List<Match> queryMatches();
	public Match queryMatchesById(Long id);
	public Integer howManyWeek(List<Match> matchesList);
}
