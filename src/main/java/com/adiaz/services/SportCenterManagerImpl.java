package com.adiaz.services;

import java.util.List;

import com.adiaz.forms.SportCenterForm;
import com.adiaz.forms.utils.SportCenterFormUtils;
import com.googlecode.objectify.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCenterDAO;
import com.adiaz.entities.SportCenter;


@Service ("sportCenterManager")
public class SportCenterManagerImpl implements SportCenterManager {

	@Autowired SportCenterDAO sportsCenterDAO;
	@Autowired SportCenterFormUtils sportCenterFormUtils;
	
	@Override
	public void addSportCenter(SportCenterForm sportCenterForm) throws Exception {
		SportCenter sportCenter = sportCenterFormUtils.formToEntity(sportCenterForm);
		sportsCenterDAO.create(sportCenter);
	}

	@Override
	public Key<SportCenter> addSportCenter(SportCenter sportCenter) throws Exception {
		return sportsCenterDAO.create(sportCenter);
	}

	@Override
	public boolean updateSportCenter(SportCenterForm sportCenterForm) throws Exception {
		SportCenter sportCenter = sportCenterFormUtils.formToEntity(sportCenterForm);
		return sportsCenterDAO.update(sportCenter);
	}

	@Override
	public List<SportCenter> querySportCenters() {
		return sportsCenterDAO.findAll();
	}

	@Override
	public List<SportCenter> querySportCenters(Long idTown) {
		return sportsCenterDAO.findByTown(idTown);
	}


	@Override
	public void removeAll() throws Exception {
		List<SportCenter> centers = sportsCenterDAO.findAll();
		for (SportCenter center : centers) {
			sportsCenterDAO.remove(center);
		}
	}

	@Override
	public SportCenterForm querySportCentersById(Long id) {
		SportCenter sportCenter = sportsCenterDAO.findById(id);
		return sportCenterFormUtils.entityToForm(sportCenter);
	}

	@Override
	public boolean removeSportCenter(Long id) throws Exception {
		return sportsCenterDAO.remove(id);
	}
	
//	@Override
//	public boolean addCourtToCenter(SportsCourtForm sportsCourtForm) throws Exception{
//		SportCenterCourt sportCourt = new SportCenterCourt();
//		sportCourt.setName(sportsCourtForm.getName());		
//		for (Long idSport : sportsCourtForm.getCourtsSports()) {
//			Key<Sport> newSport = Key.create(Sport.class, idSport);
//			sportCourt.getSports().add(Ref.create(newSport));
//		}
//		
//		//TODO: check if already exists an court with the same name.
//		Key<SportCenterCourt> newCourt = sportsCourtDAO.create(sportCourt);
//		SportCenter sportCenter = sportsCenterDAO.findById(sportsCourtForm.getIdCenter());
//		sportCenter.getCourts().add(Ref.create(newCourt));
//		sportsCenterDAO.update(sportCenter);
//		return true;
//	}
}
