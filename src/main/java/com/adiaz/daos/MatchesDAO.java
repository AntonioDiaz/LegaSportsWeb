package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.MatchesVO;

public interface MatchesDAO extends GenericDAO<MatchesVO> {	
	
	public List<MatchesVO> findByCompetition(Long competitionId);

	public List<MatchesVO> findAll();

    MatchesVO findById(Long id);
}
