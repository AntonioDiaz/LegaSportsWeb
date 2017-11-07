package com.adiaz.services;

import java.util.List;

import com.adiaz.daos.CourtDAO;
import com.adiaz.entities.Center;
import com.adiaz.forms.CenterForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CenterDAO;


@Service ("sportCenterManager")
public class CenterManagerImpl implements CenterManager {

	@Autowired
	CenterDAO sportsCenterDAO;
	@Autowired
	CourtDAO courtDAO;

	@Override
	public void addCenter(CenterForm centerForm) throws Exception {
		Center center = centerForm.formToEntity();
		sportsCenterDAO.create(center);
	}

	@Override
	public Key<Center> addCenter(Center center) throws Exception {
		return sportsCenterDAO.create(center);
	}

	@Override
	public boolean updateSportCenter(CenterForm centerForm) throws Exception {
		Center center = centerForm.formToEntity();
		return sportsCenterDAO.update(center);
	}

	@Override
	public List<Center> querySportCenters() {
		return sportsCenterDAO.findAll();
	}

	@Override
	public List<Center> querySportCenters(Long idTown) {
		return sportsCenterDAO.findByTown(idTown);
	}


	@Override
	public void removeAll() throws Exception {
		List<Center> centers = sportsCenterDAO.findAll();
		for (Center center : centers) {
			sportsCenterDAO.remove(center);
		}
	}

	@Override
	public boolean isElegibleForDelete(Long id) {
		return courtDAO.findBySportCenter(id).isEmpty();
	}

	@Override
	public CenterForm queryFormById(Long id) {
		Center center = sportsCenterDAO.findById(id);
		return new CenterForm(center);
	}

	@Override
	public Center queryById(Long id) {
		return sportsCenterDAO.findById(id);
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
