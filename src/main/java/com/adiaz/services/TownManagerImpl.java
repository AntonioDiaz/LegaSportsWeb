package com.adiaz.services;

import com.adiaz.daos.*;
import com.adiaz.entities.Club;
import com.adiaz.entities.Town;
import com.adiaz.forms.TownForm;
import com.adiaz.forms.utils.TownFormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
	@Autowired
	TeamDAO teamDAO;
	@Autowired
	SportCenterDAO sportCenterDAO;
	@Autowired
	UsersDAO usersDAO;
	@Autowired
	ClubDAO clubDAO;
	@Autowired
	CompetitionsDAO competitionsDAO;


	@Override
	public Long add(TownForm townForm) throws Exception {
		Town town = townFormUtils.formToEntity(townForm);
		return townDAO.create(town).getId();
	}

	@Override
	public boolean remove(Long id) throws Exception {
		boolean removeDone = false;
		if (isElegibleForDelete(id)) {
			Town town = new Town();
			town.setId(id);
			townDAO.remove(town);
		}
		return removeDone;
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

	@Override
	public List<Town> queryByName(String name) {
		return townDAO.findByName(name);
	}


	// TODO: 25/07/2017 remove this!!!
	@Override
	public void removeAll() throws Exception {
		List<Town> towns = townDAO.findAll();
		for (Town town : towns) {
			townDAO.remove(town);
		}
	}

	@Override
	public List<Town> queryActives() {
		List<Town> townsActives = new ArrayList<>();
		List<Town> towns = townDAO.findAll();
		for (Town town : towns) {
			if (town.isActive()) {
				townsActives.add(town);
			}
		}
		return townsActives;
	}

	@Override
	public boolean isElegibleForDelete(Long idTown) {
		return teamDAO.findByTown(idTown).isEmpty()
				&& sportCenterDAO.findByTown(idTown).isEmpty()
				&& usersDAO.findByTown(idTown).isEmpty()
				&& clubDAO.findByTownId(idTown).isEmpty()
				&& competitionsDAO.findByTown(idTown).isEmpty();
	}
}