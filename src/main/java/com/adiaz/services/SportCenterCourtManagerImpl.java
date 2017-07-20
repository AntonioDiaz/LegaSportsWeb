package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCenterCourt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCenterCourtDAO;
import com.adiaz.entities.SportCenter;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("sportCourtManager")
public class SportCenterCourtManagerImpl implements SportCenterCourtManager {

	@Autowired
	SportCenterCourtDAO sportCenterCourtDAO;

	@Override
	public Ref<SportCenterCourt> addSportCourt(SportCenterCourt sportCenterCourt) throws Exception {
		return sportCenterCourtDAO.createReturnRef(sportCenterCourt);
	}

	@Override
	public boolean updateSportCourt(SportCenterCourt sportCenterCourt) throws Exception {
		return sportCenterCourtDAO.update(sportCenterCourt);
	}

	@Override
	public boolean removeSportCourt(Long idCourt) throws Exception {
		SportCenterCourt sportCenterCourt = sportCenterCourtDAO.findSportCourt(idCourt);
		return sportCenterCourtDAO.remove(sportCenterCourt);
	}

	@Override
	public List<SportCenterCourt> querySportCourt() {
		return sportCenterCourtDAO.findAllSportCourt();
	}

	@Override
	public void removeAll() throws Exception {
		List<SportCenterCourt> courts = sportCenterCourtDAO.findAllSportCourt();
		for (SportCenterCourt court : courts) {
			sportCenterCourtDAO.remove(court);
		}
	}

	@Override
	public SportCenterCourt querySportCourt(Long idCourt) {
		return sportCenterCourtDAO.findSportCourt(idCourt);
	}

	@Override
	public Ref<SportCenterCourt> addSportCourt(SportsCourtForm sportsCourtForm) throws Exception {
		SportCenterCourt sportCenterCourt = sportsCourtForm.getCourt();
		return Ref.create(sportCenterCourtDAO.create(sportCenterCourt));
	}

	@Override
	public List<SportCenterCourt> querySportCourts(Long idSportCenter) {
		Key<SportCenter> keyCenter = Key.create(SportCenter.class, idSportCenter);
		Ref<SportCenter> refCenter = Ref.create(keyCenter);
		return sportCenterCourtDAO.findSportCourt(refCenter);
	}

	@Override
	public void updateSportCourt(SportsCourtForm sportsCourtForm) throws Exception {
		SportCenterCourt sportCenterCourt = sportsCourtForm.getCourt();
		sportCenterCourtDAO.update(sportCenterCourt);
	}
}
