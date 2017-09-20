package com.adiaz.forms.utils;

import com.adiaz.entities.Sport;
import com.adiaz.entities.Team;
import com.adiaz.entities.Town;
import com.adiaz.forms.TownForm;
import com.adiaz.forms.utils.GenericFormUtils;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toni on 17/07/2017.
 */
@Repository
public class TownFormUtils implements GenericFormUtils<TownForm, Town> {

	@Override
	public void formToEntity(Town town, TownForm form) {
		town.setName(form.getName());
		town.setAddress(form.getAddress());
		town.setEmail(form.getEmail());
		town.setContactPerson(form.getContactPerson());
		town.setPhone(form.getPhone());
		town.setActive(form.isActive());
		List<Ref<Sport>> sportsRefList = new ArrayList<>();
		for (int i = 0; i < form.getSports().length; i++) {
			Ref<Sport> sportRef = Ref.create(Key.create(Sport.class, form.getSports()[i]));
			sportsRefList.add(sportRef);
		}
		town.setSports(sportsRefList);
	}

	@Override
	public Town formToEntity(TownForm form) {
		Town town = new Town();
		formToEntity(town, form);
		return town;
	}

	@Override
	public TownForm entityToForm(Town town) {
		TownForm form = new TownForm();
		form.setId(town.getId());
		form.setName(town.getName());
		form.setAddress(town.getAddress());
		form.setContactPerson(town.getContactPerson());
		form.setEmail(town.getEmail());
		form.setPhone(town.getPhone());
		form.setActive(town.isActive());
		List<Sport> sportsList = town.getSportsDeref();
		if (sportsList!=null){
			Long sportsIds[] = new Long[sportsList.size()];
			for (int i = 0; i < sportsList.size(); i++) {
				if (sportsList.get(i)!=null) {
					sportsIds[i] = sportsList.get(i).getId();
				}
			}
			form.setSports(sportsIds);
		}
		return form;
	}
}
