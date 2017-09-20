package com.adiaz.services;

import java.util.List;

import com.adiaz.daos.SportCenterCourtDAO;
import com.adiaz.forms.SportCenterForm;
import com.adiaz.forms.utils.SportCenterFormUtils;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCenterDAO;
import com.adiaz.entities.SportCenter;


@Service ("sportCenterManager")
public class SportCenterManagerImpl implements SportCenterManager {

	@Autowired SportCenterDAO sportsCenterDAO;
	@Autowired SportCenterCourtDAO sportCenterCourtDAO;
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
	public boolean isElegibleForDelete(Long idSportCenter) {
		Ref<SportCenter> sportCenterRef = Ref.create(Key.create(SportCenter.class, idSportCenter));
		return sportCenterCourtDAO.findBySportCenter(sportCenterRef).isEmpty();
	}

	@Override
	public SportCenterForm querySportCentersById(Long id) {
		SportCenter sportCenter = sportsCenterDAO.findById(id);
		return sportCenterFormUtils.entityToForm(sportCenter);
	}

	@Override
	public boolean removeSportCenter(Long id) throws Exception {
		boolean removeDone = false;
		if (isElegibleForDelete(id)) {
			removeDone = sportsCenterDAO.remove(id);
		}
		return removeDone;
	}
}
