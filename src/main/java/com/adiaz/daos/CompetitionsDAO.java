package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.Sport;

public interface CompetitionsDAO extends GenericDAO<CompetitionsVO> {

	public List<CompetitionsVO> findCompetitions();
	public List<CompetitionsVO> findCompetitionsBySport(Sport sport);
	public List<CompetitionsVO> findCompetitions(Long sportId, Long categoryId);
	public CompetitionsVO findCompetitionsById(Long idCompetition);
	
}
