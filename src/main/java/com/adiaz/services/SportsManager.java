package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Sport;
import com.googlecode.objectify.Key;

public interface SportsManager {	
	public List<Sport> querySports();
	public Sport querySportsById(Long id);
	public Sport querySportsByName(String sportName);
	public Key<Sport> add(Sport sport) throws Exception;
	public boolean remove(Sport sport) throws Exception;
	public boolean update(Sport sport) throws Exception;

	// TODO: 24/07/2017 delete all removeAll methods :)
	public void removeAll() throws Exception;
}
