package com.adiaz.forms.utils;

import com.adiaz.entities.Category;
import com.adiaz.entities.Town;
import com.adiaz.forms.CategoriesForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Component;

/**
 * Created by toni on 04/09/2017.
 */
@Component
public class CategoriesFormUtils implements GenericFormUtils<CategoriesForm, Category>{
	@Override
	public Category formToEntity(CategoriesForm form) {
		Category category = new Category();
		formToEntity(category, form);
		return category;
	}

	@Override
	public void formToEntity(Category entity, CategoriesForm form) {
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setOrder(form.getOrder());

	}

	@Override
	public CategoriesForm entityToForm(Category entity) {
		CategoriesForm categoriesForm = new CategoriesForm();
		categoriesForm.setName(entity.getName());
		categoriesForm.setId(entity.getId());
		categoriesForm.setOrder(entity.getOrder());
		return categoriesForm;
	}
}
