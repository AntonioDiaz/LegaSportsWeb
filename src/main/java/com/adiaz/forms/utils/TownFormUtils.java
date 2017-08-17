package com.adiaz.forms.utils;

import com.adiaz.entities.Town;
import com.adiaz.forms.TownForm;
import com.adiaz.forms.utils.GenericFormUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by toni on 17/07/2017.
 */
@Repository
public class TownFormUtils implements GenericFormUtils<TownForm, Town> {

	@Override
	public void formToEntity(Town town, TownForm townForm) {
		town.setName(townForm.getName());
		town.setAddress(townForm.getAddress());
		town.setEmail(townForm.getEmail());
		town.setContactPerson(townForm.getContactPerson());
		town.setPhone(townForm.getPhone());
		town.setActive(townForm.isActive());
	}

	@Override
	public Town formToEntity(TownForm townForm) {
		Town town = new Town();
		formToEntity(town, townForm);
		return town;
	}

	@Override
	public TownForm entityToForm(Town town) {
		TownForm townForm = new TownForm();
		townForm.setId(town.getId());
		townForm.setName(town.getName());
		townForm.setAddress(town.getAddress());
		townForm.setContactPerson(town.getContactPerson());
		townForm.setEmail(town.getEmail());
		townForm.setPhone(town.getPhone());
		townForm.setActive(town.isActive());
		return townForm;
	}
}
