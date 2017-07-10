package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.MatchesVO;

public interface MatchesDAO extends GenericDAO<MatchesVO> {	
	
	public List<MatchesVO> queryMatchesByCompetition(Long competitionId);

	public List<MatchesVO> queryAllMatches();

    MatchesVO queryMatches(Long id);
}
