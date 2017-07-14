package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Category;

public interface CategoriesManager {

	public void add(Category item) throws Exception;
	public boolean remove(Category item) throws Exception;
	public boolean update(Category item) throws Exception;
	public List<Category> queryCategories();
	public Category queryCategoriesById(long id);
	public Category queryCategoriesByName(String string);
	public void removeAll() throws Exception;
}
