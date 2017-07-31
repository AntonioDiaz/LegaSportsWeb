package com.adiaz.forms.utils;

import com.adiaz.entities.*;
import com.adiaz.forms.CompetitionsForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toni on 18/07/2017.
 */
@Repository
public class CompetitionsFormUtils implements GenericFormUtils<CompetitionsForm, Competition> {


	@Override
	public Competition formToEntity(CompetitionsForm form) {
		Competition competition = new Competition();
		competition.setId(form.getId());
		competition.setName(form.getName());
		if (form.getIdCategory()!=null) {
			Ref<Category> categoryRef = Ref.create(Key.create(Category.class, form.getIdCategory()));
			competition.setCategoryRef(categoryRef);
		}
		if (form.getIdSport()!=null) {
			Ref<Sport> sportRef = Ref.create(Key.create(Sport.class, form.getIdSport()));
			competition.setSportRef(sportRef);
		}
		if (form.getIdTown()!=null) {
			Ref<Town> townRef = Ref.create(Key.create(Town.class, form.getIdTown()));
			competition.setTownRef(townRef);
		}
		if (form.getIdCourt()!=null) {
			Ref<SportCenterCourt> sportCenterCourtRef = Ref.create(Key.create(SportCenterCourt.class, form.getIdCourt()));
			competition.setSportCenterCourtRef(sportCenterCourtRef);
		}
		List<Ref<Team>> teamsRefList = new ArrayList<>();
		for (int i = 0; i < form.getTeams().length; i++) {
			Ref<Team> teamRef = Ref.create(Key.create(Team.class, form.getTeams()[i]));
			teamsRefList.add(teamRef);
		}
		competition.setTeams(teamsRefList);
		return competition;
	}

	@Override
	public CompetitionsForm entityToForm(Competition entity) {
		CompetitionsForm competitionsForm = new CompetitionsForm();
		competitionsForm.setId(entity.getId());
		competitionsForm.setName(entity.getName());
		if (entity.getCategoryEntity()!=null) {
			competitionsForm.setIdCategory(entity.getCategoryEntity().getId());
		}
		if (entity.getSportEntity()!=null) {
			competitionsForm.setIdSport(entity.getSportEntity().getId());
		}
		if (entity.getTownEntity()!=null) {
			competitionsForm.setIdTown(entity.getTownEntity().getId());
		}
		if (entity.getSportCenterCourtEntity()!=null) {
			competitionsForm.setIdCourt(entity.getSportCenterCourtEntity().getId());
		}
		if (entity.getTeams()!=null){
			Long teamsIds[] = new Long[entity.getTeams().size()];
			for (int i = 0; i < entity.getTeams().size(); i++) {
				teamsIds[i] = entity.getTeamsDeref().get(i).getId();
			}
			competitionsForm.setTeams(teamsIds);
		}
		return competitionsForm;
	}
}
