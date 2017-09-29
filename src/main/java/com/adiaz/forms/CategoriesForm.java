package com.adiaz.forms;

import com.adiaz.entities.Category;
import lombok.Data;

/**
 * Created by toni on 04/09/2017.
 */
@Data
public class CategoriesForm implements GenericForm<Category> {
	Long id;
	Integer order;
	String name;

	public CategoriesForm() { }

	public CategoriesForm(Category category) {
		this.setName(category.getName());
		this.setId(category.getId());
		this.setOrder(category.getOrder());
	}

	@Override
	public Category formToEntity() {
		return formToEntity(new Category());
	}

	@Override
	public Category formToEntity(Category entity) {
		entity.setId(this.getId());
		entity.setName(this.getName());
		entity.setOrder(this.getOrder());
		return entity;
	}

}
