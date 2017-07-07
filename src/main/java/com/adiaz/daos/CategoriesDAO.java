package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.CategoriesVO;

public interface CategoriesDAO extends GenericDAO<CategoriesVO> {	
	public List<CategoriesVO> findAllCategories();
	public CategoriesVO findCategoryById(Long id);
}
