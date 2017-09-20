package com.adiaz.daos;

import com.adiaz.entities.Town;

import java.util.List;

/**
 * Created by toni on 14/07/2017.
 */
public interface TownDAO extends GenericDAO<Town> {
	List<Town> findAll();
	Town findById(Long id);
	List<Town> findByName(String name);
	List<Town> findBySport(Long idSport);
}
