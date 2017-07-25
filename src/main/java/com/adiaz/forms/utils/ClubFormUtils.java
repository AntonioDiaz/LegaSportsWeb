package com.adiaz.forms.utils;

import com.adiaz.entities.Club;
import com.adiaz.entities.Town;
import com.adiaz.forms.ClubForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by toni on 25/07/2017.
 */
@Repository
public class ClubFormUtils implements GenericFormUtils<ClubForm, Club>{

	@Override
	public Club formToEntity(ClubForm form) {
		Club club = new Club();
		club.setId(form.getId());
		club.setName(form.getName());
		club.setContactPerson(form.getContactPerson());
		club.setContactEmail(form.getContactEmail());
		club.setContactAddress(form.getContactAddress());
		club.setContactPhone(form.getContactPhone());
		if (form.getIdTown()!=null) {
			Key<Town> key = Key.create(Town.class, form.getIdTown());
			club.setTownRef(Ref.create(key));
		}
		return club;
	}

	@Override
	public ClubForm entityToForm(Club c) {
		ClubForm  f = new ClubForm();
		f.setId(c.getId());
		f.setName(c.getName());
		f.setContactPerson(c.getContactPerson());
		f.setContactEmail(c.getContactEmail());
		f.setContactAddress(c.getContactAddress());
		f.setContactPhone(c.getContactPhone());
		if (c.getTownEntity()!=null) {
			f.setIdTown(c.getTownEntity().getId());
		}
		return f;
	}
}
