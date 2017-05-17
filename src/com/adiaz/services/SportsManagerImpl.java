package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportsDAO;
import com.adiaz.entities.SportVO;

@Service ("sportsManager")
public class SportsManagerImpl implements SportsManager {

	@Autowired
	SportsDAO sportsDAO;
	
	@Override
	public List<SportVO> querySports() {
		return sportsDAO.findAllSports();
	}

	@Override
	public SportVO querySportsById(Long id) {
		return sportsDAO.findSportById(id);
	}
	
	@Override
	public void add(SportVO sportVO) throws Exception {
		sportsDAO.create(sportVO);
	}

	@Override
	public boolean remove(SportVO sportVO) throws Exception {
		return sportsDAO.remove(sportVO);
	}

	@Override
	public boolean update(SportVO sportVO) throws Exception {
		return sportsDAO.update(sportVO);
	}

	@Override
	public SportVO querySportsByName(String sportName) {
		SportVO sportVO = null;
		List<SportVO> allSports = sportsDAO.findAllSports();
		for (SportVO sportTemp : allSports) {
			if (sportName.equalsIgnoreCase(sportTemp.getName())) {
				sportVO = sportTemp;
			}
		}
		return sportVO;
	}
}
