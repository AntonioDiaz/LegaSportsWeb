package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Category;

public interface CategoriesDAO extends GenericDAO<Category> {
	List<Category> findAllCategories();
	Category findCategoryById(Long id);

}
