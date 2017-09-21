package com.adiaz.services;

import java.util.List;

import com.adiaz.daos.*;
import com.adiaz.entities.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;

@Service ("sportsManager")
public class SportsManagerImpl implements SportsManager {

	@Autowired
	SportsDAO sportsDAO;

	@Autowired
	TownDAO townDAO;

	@Autowired
	CourtDAO courtDAO;

	@Autowired
	CompetitionsDAO competitionsDAO;

	@Autowired
	TeamDAO teamDAO;

	@Override
	public List<Sport> querySports() {
		return sportsDAO.findAll();
	}

	@Override
	public Sport querySportsById(Long id) {
		return sportsDAO.findById(id);
	}
	
	@Override
	public Key<Sport> add(Sport sport) throws Exception {
		return sportsDAO.create(sport);
	}

	@Override
	public boolean remove(Sport sport) throws Exception {
		if (isElegibleForDelete(sport.getId())) {
			return sportsDAO.remove(sport);
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Long id) throws Exception {
		if (isElegibleForDelete(id)) {
			Sport sport = new Sport();
			sport.setId(id);
			return sportsDAO.remove(sport);
		} else {
			return false;
		}
	}

	@Override
	public boolean update(Sport sport) throws Exception {
		return sportsDAO.update(sport);
	}

	@Override
	public Sport querySportsByName(String sportName) {
		Sport sport = null;
		List<Sport> allSports = sportsDAO.findAll();
		for (Sport sportTemp : allSports) {
			if (sportName.equalsIgnoreCase(sportTemp.getName())) {
				sport = sportTemp;
			}
		}
		return sport;
	}

	@Override
	public void removeAll() throws Exception {
		List<Sport> sports = sportsDAO.findAll();
		for (Sport sport : sports) {
			sportsDAO.remove(sport);
		}
	}

	/**
	 * Check if is possible to delete this sport without break references.
	 * @param idSport
	 * @return
	 */
	@Override
	public boolean isElegibleForDelete(Long idSport) {
	 	return townDAO.findBySport(idSport).isEmpty()
				&& courtDAO.findBySport(idSport).isEmpty()
				&& competitionsDAO.findBySport(idSport).isEmpty()
				&& teamDAO.findBySport(idSport).isEmpty();
	}
}
