package com.adiaz.forms.utils;

import com.adiaz.entities.Category;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;
import com.adiaz.entities.Town;
import com.adiaz.forms.CompetitionsForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

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
		return competitionsForm;
	}
}
