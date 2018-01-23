package com.adiaz.daos;

import com.adiaz.entities.Team;
import com.googlecode.objectify.Key;

import java.util.List;
import java.util.Map;

/**
 * Created by toni on 25/07/2017.
 */
public interface TeamDAO extends GenericDAO<Team>{
	List<Team> findAll();
	Team findById(Long id);
	List<Team> find(Long idTown, Long idCategory, Long idSport);
	List<Team> find(Long townId, Long categoryId, Long sportId, String name);
	List<Team> findBySport(Long idSport);
	List<Team> findByCategory(Long idCategory);
	List<Team> findByTown(Long idTown);
	List<Team> findByClub(Long idClub);
	Map<Key<Team>, Team> create(List<Team> teamList);
}
