package com.adiaz.forms;

import com.adiaz.entities.Category;
import com.adiaz.entities.Center;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

/**
 * Created by toni on 17/07/2017.
 */
@Data
public class CenterForm  implements GenericForm<Center> {
	private Long id;
	private String name;
	private String address;
	private Long idTown;

	public CenterForm() {
	}

	public CenterForm(Center center) {
		id = center.getId();
		name = center.getName();
		address = center.getAddress();
		if (center.getTownEntity()!=null) {
			idTown = center.getTownEntity().getId();
		}
	}

	@Override
	public Center formToEntity() {
		return formToEntity(new Center());
	}

	@Override
	public Center formToEntity(Center center) {
		center.setId(id);
		center.setName(name);
		center.setAddress(address);
		if (idTown!=null) {
			Key<Town> key = Key.create(Town.class, idTown);
			center.setTownRef(Ref.create(key));
		}
		return center;
	}
}
