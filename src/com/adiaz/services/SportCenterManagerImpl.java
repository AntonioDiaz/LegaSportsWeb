package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCenterDAO;
import com.adiaz.daos.SportCourtDAO;
import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCourt;
import com.adiaz.entities.SportVO;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;


@Service ("sportCenterManager")
public class SportCenterManagerImpl implements SportCenterManager {

	@Autowired SportCenterDAO sportsCenterDAO;
	@Autowired SportCourtDAO sportsCourtDAO;
	
	@Override
	public void addSportCenter(SportCenter sportCenter) throws Exception {
		sportsCenterDAO.create(sportCenter);
	}

	@Override
	public boolean updateSportCenter(SportCenter sportCenter) throws Exception {
		return sportsCenterDAO.update(sportCenter);
	}

	@Override
	public boolean removeSportCenter(SportCenter sportCenter) throws Exception {
		return sportsCenterDAO.update(sportCenter);
	}

	@Override
	public List<SportCenter> querySportCenters() {
		return sportsCenterDAO.findAllSportsCenters();
	}

	@Override
	public void removeAll() throws Exception {
		List<SportCenter> centers = sportsCenterDAO.findAllSportsCenters();
		for (SportCenter center : centers) {
			sportsCenterDAO.remove(center);
		}
	}

	@Override
	public SportCenter querySportCentersById(Long id) {
		return sportsCenterDAO.findSportsCenterById(id);
	}

	@Override
	public boolean removeSportCenter(Long id) throws Exception {
		return sportsCenterDAO.remove(id);
	}

	@Override
	public boolean addCourtToCenter(SportsCourtForm sportsCourtForm) throws Exception{
		SportCourt sportCourt = new SportCourt();
		sportCourt.setName(sportsCourtForm.getName());		
		for (Long idSport : sportsCourtForm.getCourtsSports()) {
			Key<SportVO> newSport = Key.create(SportVO.class, idSport);
			sportCourt.getSports().add(Ref.create(newSport));
		}
		
		
		//TODO: check if already exists an court with the same name.
		Key<SportCourt> newCourt = sportsCourtDAO.create(sportCourt);
		SportCenter sportCenter = sportsCenterDAO.findSportsCenterById(sportsCourtForm.getIdCenter());
		sportCenter.getCourts().add(Ref.create(newCourt));
		sportsCenterDAO.update(sportCenter);
		return true;
	}
}
