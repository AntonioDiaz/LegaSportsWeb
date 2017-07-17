package com.adiaz.services;

import com.adiaz.daos.TownDAO;
import com.adiaz.entities.Town;
import com.adiaz.forms.SportCenterFormUtils;
import com.adiaz.forms.TownForm;
import com.adiaz.forms.TownFormUtils;
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

	@Override
	public Long add(TownForm townForm) throws Exception {
		Town town = TownFormUtils.generateTownEntityFromForm(townForm);
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
		Town town = TownFormUtils.generateTownEntityFromForm(townForm);
		town.setId(id);
		return townDAO.update(town);
	}

	@Override
	public List<Town> queryAll() {
		return townDAO.findAll();
	}

	@Override
	public TownForm queryById(Long id) {
		return TownFormUtils.generateTownFormFromEntity (townDAO.findById(id));
	}


	@Override
	public void removeAll() throws Exception {
		List<Town> towns = townDAO.findAll();
		for (Town town : towns) {
			townDAO.remove(town);
		}
	}
}
