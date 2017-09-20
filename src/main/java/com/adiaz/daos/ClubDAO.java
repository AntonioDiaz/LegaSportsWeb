package com.adiaz.daos;

import com.adiaz.entities.Club;

import java.util.List;

/**
 * Created by toni on 24/07/2017.
 */
public interface ClubDAO extends GenericDAO<Club> {
	Club findById(Long id);
	List<Club> findAll();
	List<Club> findByTownId(Long townId);
}
