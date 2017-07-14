package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Sport;

public interface SportsDAO extends GenericDAO<Sport>{
	public List<Sport> findAllSports();
	public Sport findSportById(Long id);
}
