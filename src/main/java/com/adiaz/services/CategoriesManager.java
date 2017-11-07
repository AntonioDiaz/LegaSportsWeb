package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Category;
import com.adiaz.forms.CategoriesFilterForm;
import com.adiaz.forms.CategoriesForm;

public interface CategoriesManager {
	void add(Category item) throws Exception;
	void add(CategoriesForm categoriesForm) throws Exception;
	boolean remove(Long id) throws Exception;
	boolean update(Category item) throws Exception;
	boolean update(CategoriesForm categoriesForm) throws Exception;
	List<Category> queryCategories();
	Category queryCategoriesById(long id);
	CategoriesForm queryCategoriesFormById(long id);
	void removeAll() throws Exception;
	boolean isElegibleForDelete(Long idCategory);
}
