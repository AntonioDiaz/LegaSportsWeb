package com.adiaz.forms.utils;

import com.adiaz.entities.Center;
import com.adiaz.entities.Town;
import com.adiaz.forms.CenterForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

/**
 * Created by toni on 17/07/2017.
 */
@Repository
public class CenterFormUtils implements GenericFormUtils<CenterForm, Center> {

	@Override
	public void formToEntity(Center center, CenterForm centerForm) {
		if (centerForm !=null) {
			center.setId(centerForm.getId());
			center.setName(centerForm.getName());
			center.setAddress(centerForm.getAddress());
			if (centerForm.getIdTown()!=null) {
				Key<Town> key = Key.create(Town.class, centerForm.getIdTown());
				center.setTownRef(Ref.create(key));
			}
		}
	}

	@Override
	public Center formToEntity(CenterForm centerForm) {
		Center center = new Center();
		formToEntity(center, centerForm);
		return center;
	}

	@Override
	public CenterForm entityToForm(Center center) {
		CenterForm centerForm = new CenterForm();
		centerForm.setId(center.getId());
		centerForm.setName(center.getName());
		centerForm.setAddress(center.getAddress());
		if (center.getTownEntity()!=null) {
			centerForm.setIdTown(center.getTownEntity().getId());
		}
		return centerForm;
	}
}
