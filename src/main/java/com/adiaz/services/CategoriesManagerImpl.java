package com.adiaz.services;

import java.util.List;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.TeamDAO;
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
	@Autowired
	TeamDAO teamDAO;
	@Autowired
	CompetitionsDAO competitionsDAO;


	@Override
	public void add(Category item) throws Exception {
		categoriesDAO.create(item);
	}

	@Override
	public boolean remove(Category item) throws Exception {
		if (this.isElegibleForDelete(item.getId())) {
			return categoriesDAO.remove(item);
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Long id) throws Exception {
		if (this.isElegibleForDelete(id)) {
			Category category = new Category();
			category.setId(id);
			return categoriesDAO.remove(category);
		} else {
			return false;
		}
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
		return categoriesDAO.findAll();
	}

	@Override
	public Category queryCategoriesById(long id) {
		return categoriesDAO.findById(id);
	}

	@Override
	public CategoriesForm queryCategoriesFormById(long id) {
		CategoriesForm categoriesForm = null;
		Category categoryById = categoriesDAO.findById(id);
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
		List<Category> categories = categoriesDAO.findAll();
		for (Category category : categories) {
			categoriesDAO.remove(category);
		}		
	}

	@Override
	public void add(CategoriesForm categoriesForm) throws Exception {
		Category category = categoriesFormUtils.formToEntity(categoriesForm);
		categoriesDAO.create(category);
	}

	@Override
	public boolean isElegibleForDelete(Long idCategory) {
		return teamDAO.findByCategory(idCategory).isEmpty() && competitionsDAO.findByCategory(idCategory).isEmpty();
	}
}
