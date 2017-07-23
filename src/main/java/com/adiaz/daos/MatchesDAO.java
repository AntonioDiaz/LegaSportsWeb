package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Match;

public interface MatchesDAO extends GenericDAO<Match> {
	
	public List<Match> findByCompetition(Long competitionId);

	public List<Match> findByCompetition(Long competitionId, boolean workingCopy);

	public List<Match> findAll();

    Match findById(Long id);
}
