package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Match;

public interface MatchesDAO extends GenericDAO<Match> {
	
	List<Match> findByCompetition(Long competitionId);
	List<Match> findByCourt(Long idCourt);
	List<Match> findByCompetition(Long competitionId, boolean workingCopy);
	List<Match> findAll();
    Match findById(Long id);

}
