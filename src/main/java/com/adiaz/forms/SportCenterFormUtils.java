package com.adiaz.forms;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

/**
 * Created by toni on 17/07/2017.
 */
public class SportCenterFormUtils {

	public static SportCenter formToEntity(SportCenterForm sportCenterForm) {
		SportCenter sportCenter = new SportCenter();
		if (sportCenter!=null) {
			sportCenter.setId(sportCenterForm.getId());
			sportCenter.setName(sportCenterForm.getName());
			sportCenter.setAddress(sportCenterForm.getAddress());
			if (sportCenterForm.getIdTown()!=null) {
				Key<Town> key = Key.create(Town.class, sportCenterForm.getIdTown());
				sportCenter.setTownRef(Ref.create(key));
			}
		}
		return sportCenter;
	}


	public static SportCenterForm entityToForm(SportCenter sportCenter) {
		SportCenterForm sportCenterForm = new SportCenterForm();
		sportCenterForm.setId(sportCenter.getId());
		sportCenterForm.setName(sportCenter.getName());
		sportCenterForm.setAddress(sportCenter.getAddress());
		if (sportCenter.getTown()!=null) {
			sportCenterForm.setIdTown(sportCenter.getTown().getId());
		}
		return sportCenterForm;
	}
}
