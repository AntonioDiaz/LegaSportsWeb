package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportsDAO;
import com.googlecode.objectify.Key;

@Service ("sportsManager")
public class SportsManagerImpl implements SportsManager {

	@Autowired
	SportsDAO sportsDAO;
	
	@Override
	public List<Sport> querySports() {
		return sportsDAO.findAllSports();
	}

	@Override
	public Sport querySportsById(Long id) {
		return sportsDAO.findSportById(id);
	}
	
	@Override
	public Key<Sport> add(Sport sport) throws Exception {
		return sportsDAO.create(sport);
	}

	@Override
	public boolean remove(Sport sport) throws Exception {
		return sportsDAO.remove(sport);
	}

	@Override
	public boolean remove(Long id) throws Exception {
		Sport sport = new Sport();
		sport.setId(id);
		return sportsDAO.remove(sport);
	}

	@Override
	public boolean update(Sport sport) throws Exception {
		return sportsDAO.update(sport);
	}

	@Override
	public Sport querySportsByName(String sportName) {
		Sport sport = null;
		List<Sport> allSports = sportsDAO.findAllSports();
		for (Sport sportTemp : allSports) {
			if (sportName.equalsIgnoreCase(sportTemp.getName())) {
				sport = sportTemp;
			}
		}
		return sport;
	}

	@Override
	public void removeAll() throws Exception {
		List<Sport> sports = sportsDAO.findAllSports();
		for (Sport sport : sports) {
			sportsDAO.remove(sport);
		}
	}
}
