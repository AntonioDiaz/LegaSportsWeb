package com.adiaz.services;

import java.util.List;

import com.adiaz.forms.CategoriesFilterForm;
import com.adiaz.forms.CategoriesForm;
import com.adiaz.forms.utils.CategoriesFormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CategoriesDAO;
import com.adiaz.entities.Category;

@Service ("categoriesManager")
public class CategoriesManagerImpl implements CategoriesManager {

	@Autowired
	CategoriesDAO categoriesDAO;
	@Autowired
	CategoriesFormUtils categoriesFormUtils;
	
	@Override
	public void add(Category item) throws Exception {
		categoriesDAO.create(item);
	}

	@Override
	public boolean remove(Category item) throws Exception {
		return categoriesDAO.remove(item);
	}

	@Override
	public void remove(Long id) throws Exception {
		Category category = new Category();
		category.setId(id);
		categoriesDAO.remove(category);
	}

	@Override
	public boolean update(Category item) throws Exception {
		return categoriesDAO.update(item);
	}

	@Override
	public boolean update(CategoriesForm categoriesForm) throws Exception {
		Category category = queryCategoriesById(categoriesForm.getId());
		categoriesFormUtils.formToEntity(category, categoriesForm);
		return categoriesDAO.update(category);
	}

	@Override
	public List<Category> queryCategories() {
		return categoriesDAO.findAllCategories();
	}

	@Override
	public Category queryCategoriesById(long id) {
		return categoriesDAO.findCategoryById(id);
	}

	@Override
	public CategoriesForm queryCategoriesFormById(long id) {
		CategoriesForm categoriesForm = null;
		Category categoryById = categoriesDAO.findCategoryById(id);
		if (categoryById!=null) {
			categoriesForm = categoriesFormUtils.entityToForm(categoryById);
		}
		return categoriesForm;
	}

	@Override
	public Category queryCategoriesByName(String string) {
		Category category = null;
		List<Category> queryCategories = this.queryCategories();
		for (Category c : queryCategories) {
			if (c.getName().equals(string)) {
				category = c;
			}
		}
		return category;
	}

	@Override
	public void removeAll() throws Exception {
		List<Category> categories = categoriesDAO.findAllCategories();
		for (Category category : categories) {
			categoriesDAO.remove(category);
		}		
	}

	@Override
	public void add(CategoriesForm categoriesForm) throws Exception {
		Category category = categoriesFormUtils.formToEntity(categoriesForm);
		categoriesDAO.create(category);
	}
}
