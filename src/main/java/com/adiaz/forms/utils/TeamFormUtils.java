package com.adiaz.forms.utils;

import com.adiaz.entities.Category;
import com.adiaz.entities.Club;
import com.adiaz.entities.Team;
import com.adiaz.entities.Town;
import com.adiaz.forms.TeamForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

/**
 * Created by toni on 25/07/2017.
 */
@Repository
public class TeamFormUtils implements GenericFormUtils<TeamForm, Team> {

	@Override
	public Team formToEntity(TeamForm f) {
		Team team = new Team();
		team.setId(f.getId());
		team.setName(f.getName());
		if (f.getIdCategory()!=null) {
			Key<Category> key = Key.create(Category.class, f.getIdCategory());
			team.setCategoryRef(Ref.create(key));
		}
		if (f.getIdTown()!=null) {
			Key<Town> key = Key.create(Town.class, f.getIdTown());
			team.setTownRef(Ref.create(key));
		}
		if (f.getIdClub()!=null) {
			Key<Club> key = Key.create(Club.class, f.getIdClub());
			team.setClubRef(Ref.create(key));
		}
		return team;
	}

	@Override
	public TeamForm entityToForm(Team entity) {
		TeamForm f = new TeamForm();
		f.setId(entity.getId());
		f.setName(entity.getName());
		if (entity.getCategoryEntity()!=null) {
			f.setIdCategory(entity.getCategoryEntity().getId());
		}
		if (entity.getTownEntity()!=null) {
			f.setIdTown(entity.getTownEntity().getId());
		}
		if (entity.getClubEntity()!=null) {
			f.setIdClub(entity.getClubEntity().getId());
		}
		return f;
	}
}
