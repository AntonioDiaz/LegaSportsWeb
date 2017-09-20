package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;

public interface CompetitionsDAO extends GenericDAO<Competition> {
	List<Competition> findCompetitions();
	List<Competition> findCompetitionsBySport(Long idSport);
	List<Competition> findCompetitionsByCategory(Long idCategory);
	List<Competition> findCompetitions(Long sportId, Long idCategory, Long idTown);
	List<Competition> findCompetitions(Long idTown, boolean onlyPublished);
	List<Competition> findByTown(Long idTown);
	Competition findCompetitionsById(Long idCompetition);
}
