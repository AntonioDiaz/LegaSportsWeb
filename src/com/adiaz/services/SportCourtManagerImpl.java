package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCourtDAO;
import com.adiaz.entities.SportCourt;
import com.googlecode.objectify.Ref;

@Service ("sportCourtManager")
public class SportCourtManagerImpl  implements SportCourtManager {

	@Autowired SportCourtDAO sportCourtDAO;

	@Override
	public Ref<SportCourt> addSportCourt(SportCourt sportCourt) throws Exception {
		return sportCourtDAO.createReturnRef(sportCourt);
	}

	@Override
	public boolean updateSportCourt(SportCourt sportCourt) throws Exception {
		return sportCourtDAO.update(sportCourt);
	}

	@Override
	public boolean removeSportCourt(SportCourt sportCourt) throws Exception {
		return sportCourtDAO.remove(sportCourt);
	}

	@Override
	public List<SportCourt> querySportCourt() {
		return sportCourtDAO.findAllSportCourt();
	}

	@Override
	public void removeAll() throws Exception {
		List<SportCourt> courts = sportCourtDAO.findAllSportCourt();
		for (SportCourt court : courts) {
			sportCourtDAO.remove(court);
		}
	}
}
