package com.adiaz.forms;

import com.adiaz.entities.Sport;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toni on 14/07/2017.
 */
@Data
public class TownForm implements GenericForm<Town> {
	private Long id;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String contactPerson;
	private boolean isActive;
	private Long[] sports;
	private String iconName;

	public TownForm() {
	}

	public TownForm(Town town) {
		id = town.getId();
		name = town.getName();
		address = town.getAddress();
		contactPerson = town.getContactPerson();
		email = town.getEmail();
		phone = town.getPhone();
		isActive = town.isActive();
		iconName = town.getIconName();
		List<Sport> sportsList = town.getSportsDeref();
		if (sportsList!=null){
			sports = new Long[sportsList.size()];
			for (int i = 0; i < sportsList.size(); i++) {
				if (sportsList.get(i)!=null) {
					sports[i] = sportsList.get(i).getId();
				}
			}
		}
	}

	@Override
	public Town formToEntity() {
		return formToEntity(new Town());
	}

	@Override
	public Town formToEntity(Town town) {
		town.setName(name);
		town.setAddress(address);
		town.setEmail(email);
		town.setContactPerson(contactPerson);
		town.setPhone(phone);
		town.setActive(isActive);
		town.setIconName(iconName);
		List<Ref<Sport>> sportsRefList = new ArrayList<>();
		if (sports!=null) {
			for (int i = 0; i < sports.length; i++) {
				Ref<Sport> sportRef = Ref.create(Key.create(Sport.class, sports[i]));
				sportsRefList.add(sportRef);
			}
		}
		town.setSports(sportsRefList);
		return town;
	}
}
