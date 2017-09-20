package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Sport;
import com.googlecode.objectify.Key;

public interface SportsManager {	
	List<Sport> querySports();
	Sport querySportsById(Long id);
	Sport querySportsByName(String sportName);
	Key<Sport> add(Sport sport) throws Exception;
	boolean remove(Sport sport) throws Exception;
	boolean remove(Long id) throws Exception;
	boolean update(Sport sport) throws Exception;
	// TODO: 24/07/2017 delete all removeAll methods :)
	void removeAll() throws Exception;
	boolean isElegibleForDelete(Long idSport);
}
