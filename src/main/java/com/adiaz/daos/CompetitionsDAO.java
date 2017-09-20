package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;

public interface CompetitionsDAO extends GenericDAO<Competition> {
	List<Competition> findAll();
	List<Competition> findBySport(Long idSport);
	List<Competition> findByCategory(Long idCategory);
	List<Competition> find(Long sportId, Long idCategory, Long idTown);
	List<Competition> find(Long idTown, boolean onlyPublished);
	List<Competition> findByTown(Long idTown);
	List<Competition> findByTeam(Long idTeam);
	Competition findById(Long idCompetition);
}
