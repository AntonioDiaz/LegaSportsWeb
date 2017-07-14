package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;

public interface CompetitionsDAO extends GenericDAO<Competition> {

	public List<Competition> findCompetitions();
	public List<Competition> findCompetitionsBySport(Sport sport);
	public List<Competition> findCompetitions(Long sportId, Long categoryId);
	public Competition findCompetitionsById(Long idCompetition);
	
}
