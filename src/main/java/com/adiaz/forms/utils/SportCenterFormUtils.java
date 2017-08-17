package com.adiaz.forms.utils;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.Town;
import com.adiaz.forms.SportCenterForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

/**
 * Created by toni on 17/07/2017.
 */
@Repository
public class SportCenterFormUtils implements GenericFormUtils<SportCenterForm, SportCenter> {

	@Override
	public void formToEntity(SportCenter sportCenter, SportCenterForm sportCenterForm) {
		if (sportCenterForm!=null) {
			sportCenter.setId(sportCenterForm.getId());
			sportCenter.setName(sportCenterForm.getName());
			sportCenter.setAddress(sportCenterForm.getAddress());
			if (sportCenterForm.getIdTown()!=null) {
				Key<Town> key = Key.create(Town.class, sportCenterForm.getIdTown());
				sportCenter.setTownRef(Ref.create(key));
			}
		}
	}

	@Override
	public SportCenter formToEntity(SportCenterForm sportCenterForm) {
		SportCenter sportCenter = new SportCenter();
		formToEntity(sportCenter, sportCenterForm);
		return sportCenter;
	}

	@Override
	public SportCenterForm entityToForm(SportCenter sportCenter) {
		SportCenterForm sportCenterForm = new SportCenterForm();
		sportCenterForm.setId(sportCenter.getId());
		sportCenterForm.setName(sportCenter.getName());
		sportCenterForm.setAddress(sportCenter.getAddress());
		if (sportCenter.getTownEntity()!=null) {
			sportCenterForm.setIdTown(sportCenter.getTownEntity().getId());
		}
		return sportCenterForm;
	}
}
