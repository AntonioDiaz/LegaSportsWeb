package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCenterCourt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCourtDAO;
import com.adiaz.entities.SportCenter;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("sportCourtManager")
public class SportCourtManagerImpl implements SportCourtManager {

	@Autowired SportCourtDAO sportCourtDAO;

	@Override
	public Ref<SportCenterCourt> addSportCourt(SportCenterCourt sportCenterCourt) throws Exception {
		return sportCourtDAO.createReturnRef(sportCenterCourt);
	}

	@Override
	public boolean updateSportCourt(SportCenterCourt sportCenterCourt) throws Exception {
		return sportCourtDAO.update(sportCenterCourt);
	}

	@Override
	public boolean removeSportCourt(Long idCourt) throws Exception {
		SportCenterCourt sportCenterCourt = sportCourtDAO.findSportCourt(idCourt);
		return sportCourtDAO.remove(sportCenterCourt);
	}

	@Override
	public List<SportCenterCourt> querySportCourt() {
		return sportCourtDAO.findAllSportCourt();
	}

	@Override
	public void removeAll() throws Exception {
		List<SportCenterCourt> courts = sportCourtDAO.findAllSportCourt();
		for (SportCenterCourt court : courts) {
			sportCourtDAO.remove(court);
		}
	}

	@Override
	public SportCenterCourt querySportCourt(Long idCourt) {
		return sportCourtDAO.findSportCourt(idCourt);
	}

	@Override
	public Ref<SportCenterCourt> addSportCourt(SportsCourtForm sportsCourtForm) throws Exception {
		SportCenterCourt sportCenterCourt = sportsCourtForm.getCourt();
		return Ref.create(sportCourtDAO.create(sportCenterCourt));
	}

	@Override
	public List<SportCenterCourt> querySportCourts(Long idSportCenter) {
		Key<SportCenter> keyCenter = Key.create(SportCenter.class, idSportCenter);
		Ref<SportCenter> refCenter = Ref.create(keyCenter);
		return sportCourtDAO.findSportCourt(refCenter);
	}

	@Override
	public void updateSportCourt(SportsCourtForm sportsCourtForm) throws Exception {
		SportCenterCourt sportCenterCourt = sportsCourtForm.getCourt();
		sportCourtDAO.update(sportCenterCourt);
	}
}
