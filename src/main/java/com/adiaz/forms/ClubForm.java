package com.adiaz.forms;

import com.adiaz.entities.Club;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

/**
 * Created by toni on 25/07/2017.
 */
@Data
public class ClubForm implements GenericForm<Club>{
	private Long id;
	private String name;
	private String contactPerson;
	private String contactEmail;
	private String contactAddress;
	private String contactPhone;
	private Long idTown;

	public ClubForm() {
	}

	public ClubForm(Club c) {
		id = c.getId();
		name = c.getName();
		contactPerson = c.getContactPerson();
		contactEmail = c.getContactEmail();
		contactAddress = c.getContactAddress();
		contactPhone = c.getContactPhone();
		if (c.getTownEntity() != null) {
			idTown = c.getTownEntity().getId();
		}
	}

	@Override
	public Club formToEntity() {
		return formToEntity(new Club());
	}

	@Override
	public Club formToEntity(Club club) {
		club.setId(id);
		club.setName(name);
		club.setContactPerson(contactPerson);
		club.setContactEmail(contactEmail);
		club.setContactAddress(contactAddress);
		club.setContactPhone(contactPhone);
		if (idTown!=null) {
			Key<Town> key = Key.create(Town.class, idTown);
			club.setTownRef(Ref.create(key));
		}
		return club;
	}
}
