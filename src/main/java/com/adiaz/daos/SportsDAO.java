package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Sport;

public interface SportsDAO extends GenericDAO<Sport>{
	List<Sport> findAllSports();
	Sport findSportById(Long id);
}
