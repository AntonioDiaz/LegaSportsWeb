package com.adiaz.services;

import com.adiaz.daos.TownDAO;
import com.adiaz.entities.Town;
import com.adiaz.forms.TownForm;
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
	public void add(TownForm townForm) throws Exception {
		Town town = generateTownEntityFromForm(townForm);
		townDAO.create(town);
	}

	@Override
	public boolean remove(Long id) throws Exception {
		Town town = new Town();
		town.setId(id);
		return townDAO.remove(town);
	}

	@Override
	public boolean update(Long id, TownForm townForm) throws Exception {
		Town town = generateTownEntityFromForm(townForm);
		town.setId(id);
		return townDAO.update(town);
	}

	private Town generateTownEntityFromForm(TownForm townForm) {
		Town town = new Town();
		town.setName(townForm.getName());
		town.setAddress(townForm.getAddress());
		town.setEmail(townForm.getEmail());
		town.setContactPerson(townForm.getContactPerson());
		town.setPhone(townForm.getPhone());
		town.setActive(townForm.isActive());
		return town;
	}

	@Override
	public List<Town> queryAll() {
		return townDAO.findAll();
	}

	@Override
	public TownForm queryById(Long id) {
		return generateTownFormFromEntity (townDAO.findById(id));
	}

	private TownForm generateTownFormFromEntity(Town townEntity) {
		TownForm townForm = new TownForm();
		townForm.setId(townEntity.getId());
		townForm.setName(townEntity.getName());
		townForm.setAddress(townEntity.getAddress());
		townForm.setContactPerson(townEntity.getContactPerson());
		townForm.setEmail(townEntity.getEmail());
		townForm.setPhone(townEntity.getPhone());
		townForm.setActive(townEntity.isActive());
		return townForm;
	}

	@Override
	public void removeAll() throws Exception {
		List<Town> towns = townDAO.findAll();
		for (Town town : towns) {
			townDAO.remove(town);
		}
	}
}
