package com.adiaz.forms;

import com.adiaz.entities.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

/**
 * Created by toni on 25/07/2017.
 */
@Data
public class TeamForm implements GenericForm<Team>{
	private Long id;
	private String name;
	private Long idCategory;
	private Long idTown;
	private Long idClub;
	private Long idSport;

	public TeamForm() {
	}

	public TeamForm(Team team) {
		id = team.getId();
		name = team.getName();
		if (team.getCategoryEntity()!=null) {
			idCategory = team.getCategoryEntity().getId();
		}
		if (team.getTownEntity()!=null) {
			idTown = team.getTownEntity().getId();
		}
		if (team.getClubEntity()!=null) {
			idClub = team.getClubEntity().getId();
		}
		if (team.getSportEntity()!=null) {
			idSport = team.getSportEntity().getId();
		}
	}


	@Override
	public Team formToEntity() {
		return formToEntity(new Team());
	}

	@Override
	public Team formToEntity(Team team) {
		team.setId(id);
		team.setName(name);
		if (idCategory!=null) {
			Key<Category> key = Key.create(Category.class, idCategory);
			team.setCategoryRef(Ref.create(key));
		}
		if (idTown!=null) {
			Key<Town> key = Key.create(Town.class, idTown);
			team.setTownRef(Ref.create(key));
		}
		if (idClub!=null) {
			Key<Club> key = Key.create(Club.class, idClub);
			team.setClubRef(Ref.create(key));
		}
		if (idSport!=null) {
			Key<Sport> key = Key.create(Sport.class, idSport);
			team.setSportRef(Ref.create(key));
		}
		return team;
	}
}
