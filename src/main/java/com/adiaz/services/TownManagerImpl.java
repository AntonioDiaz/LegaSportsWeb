package com.adiaz.services;

import com.adiaz.daos.TownDAO;
import com.adiaz.entities.Town;
import com.adiaz.forms.TownForm;
import com.adiaz.forms.utils.TownFormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by toni on 14/07/2017.
 */
@Component
public class TownManagerImpl implements TownManager {

	@Autowired
	TownDAO townDAO;
	@Autowired
	TownFormUtils townFormUtils;

	@Override
	public Long add(TownForm townForm) throws Exception {
		Town town = townFormUtils.formToEntity(townForm);
		return townDAO.create(town).getId();
	}

	@Override
	public boolean remove(Long id) throws Exception {
		Town town = new Town();
		town.setId(id);
		return townDAO.remove(town);
	}

	@Override
	public boolean update(Long id, TownForm townForm) throws Exception {
		Town town = townFormUtils.formToEntity(townForm);
		town.setId(id);
		return townDAO.update(town);
	}

	@Override
	public List<Town> queryAll() {
		return townDAO.findAll();
	}

	@Override
	public TownForm queryById(Long id) {
		return townFormUtils.entityToForm(townDAO.findById(id));
	}


	// TODO: 25/07/2017 remove this!!!
	@Override
	public void removeAll() throws Exception {
		List<Town> towns = townDAO.findAll();
		for (Town town : towns) {
			townDAO.remove(town);
		}
	}
}
