package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.CategoriesVO;

public interface CategoriesManager {

	public void add(CategoriesVO item) throws Exception;	
	public boolean remove(CategoriesVO item) throws Exception;	
	public boolean update(CategoriesVO item) throws Exception;
	public List<CategoriesVO> queryCategories();
	public CategoriesVO queryCategoriesById(long id);
	public CategoriesVO queryCategoriesByName(String string);
}
