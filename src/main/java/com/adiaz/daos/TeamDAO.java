package com.adiaz.daos;

import com.adiaz.entities.Team;
import com.adiaz.entities.Town;

import java.util.List;

/**
 * Created by toni on 25/07/2017.
 */
public interface TeamDAO extends GenericDAO<Team>{
	public List<Team> findAll();
	public Team findById(Long id);
	public List<Team> find(Long idTown, Long idCategory, Long idSport);
	public List<Team> find(Long townId, Long categoryId, Long sportId, String name);
}
