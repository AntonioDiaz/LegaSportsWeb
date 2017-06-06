package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCourtDAO;
import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCourt;
import com.adiaz.entities.SportVO;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("sportCourtManager")
public class SportCourtManagerImpl implements SportCourtManager {

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
	public boolean removeSportCourt(Long idCourt) throws Exception {
		SportCourt sportCourt = sportCourtDAO.findSportCourt(idCourt);
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

	@Override
	public SportCourt querySportCourt(Long idCourt) {		
		return sportCourtDAO.findSportCourt(idCourt);
	}

	@Override
	public Ref<SportCourt> addSportCourt(SportsCourtForm sportsCourtForm) throws Exception {
		SportCourt sportCourt = new SportCourt();
		sportCourt.setName(sportsCourtForm.getName());		
		for (Long idSport : sportsCourtForm.getCourtsSports()) {
			Key<SportVO> newSport = Key.create(SportVO.class, idSport);
			sportCourt.getSports().add(Ref.create(newSport));
		}		
		Key<SportCenter> refCenter = Key.create(SportCenter.class, sportsCourtForm.getIdCenter());
		sportCourt.setCenter(Ref.create(refCenter));
		return Ref.create(sportCourtDAO.create(sportCourt));
	}

	@Override
	public List<SportCourt> querySportCourts(Long idSportCenter) {
		Key<SportCenter> keyCenter = Key.create(SportCenter.class, idSportCenter);
		Ref<SportCenter> refCenter = Ref.create(keyCenter);
		return sportCourtDAO.findSportCourt(refCenter);
	}
}
