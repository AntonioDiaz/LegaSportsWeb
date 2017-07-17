package com.adiaz.forms;

import com.adiaz.entities.Town;

/**
 * Created by toni on 17/07/2017.
 */
public class TownFormUtils {

	public static Town generateTownEntityFromForm(TownForm townForm) {
		Town town = new Town();
		town.setName(townForm.getName());
		town.setAddress(townForm.getAddress());
		town.setEmail(townForm.getEmail());
		town.setContactPerson(townForm.getContactPerson());
		town.setPhone(townForm.getPhone());
		town.setActive(townForm.isActive());
		return town;
	}

	public static TownForm generateTownFormFromEntity(Town townEntity) {
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


}
