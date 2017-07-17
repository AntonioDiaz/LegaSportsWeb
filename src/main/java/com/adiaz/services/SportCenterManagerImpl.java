package com.adiaz.services;

import java.util.List;

import com.adiaz.forms.SportCenterForm;
import com.adiaz.forms.SportCenterFormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCenterDAO;
import com.adiaz.entities.SportCenter;


@Service ("sportCenterManager")
public class SportCenterManagerImpl implements SportCenterManager {

	@Autowired SportCenterDAO sportsCenterDAO;
	
	@Override
	public void addSportCenter(SportCenterForm sportCenterForm) throws Exception {
		SportCenter sportCenter = SportCenterFormUtils.formToEntity(sportCenterForm);
		sportsCenterDAO.create(sportCenter);
	}

	@Override
	public boolean updateSportCenter(SportCenterForm sportCenterForm) throws Exception {
		SportCenter sportCenter = SportCenterFormUtils.formToEntity(sportCenterForm);
		return sportsCenterDAO.update(sportCenter);
	}

	@Override
	public List<SportCenter> querySportCenters() {
		return sportsCenterDAO.findAllSportsCenters();
	}

	@Override
	public List<SportCenter> querySportCenters(Long idTown) {
		return sportsCenterDAO.findSportsCenterByTown(idTown);
	}


	@Override
	public void removeAll() throws Exception {
		List<SportCenter> centers = sportsCenterDAO.findAllSportsCenters();
		for (SportCenter center : centers) {
			sportsCenterDAO.remove(center);
		}
	}

	@Override
	public SportCenterForm querySportCentersById(Long id) {
		SportCenter sportCenter = sportsCenterDAO.findSportsCenterById(id);
		return SportCenterFormUtils.entityToForm(sportCenter);
	}

	@Override
	public boolean removeSportCenter(Long id) throws Exception {
		return sportsCenterDAO.remove(id);
	}
	
//	@Override
//	public boolean addCourtToCenter(SportsCourtForm sportsCourtForm) throws Exception{
//		SportCourt sportCourt = new SportCourt();
//		sportCourt.setName(sportsCourtForm.getName());		
//		for (Long idSport : sportsCourtForm.getCourtsSports()) {
//			Key<Sport> newSport = Key.create(Sport.class, idSport);
//			sportCourt.getSports().add(Ref.create(newSport));
//		}
//		
//		//TODO: check if already exists an court with the same name.
//		Key<SportCourt> newCourt = sportsCourtDAO.create(sportCourt);
//		SportCenter sportCenter = sportsCenterDAO.findSportsCenterById(sportsCourtForm.getIdCenter());
//		sportCenter.getCourts().add(Ref.create(newCourt));
//		sportsCenterDAO.update(sportCenter);
//		return true;
//	}
}
