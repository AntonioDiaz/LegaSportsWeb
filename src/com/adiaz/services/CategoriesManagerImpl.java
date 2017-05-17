package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CategoriesDAO;
import com.adiaz.entities.CategoriesVO;

@Service ("categoriesManager")
public class CategoriesManagerImpl implements CategoriesManager {

	@Autowired
	CategoriesDAO categoriesDAO;
	
	@Override
	public void add(CategoriesVO item) throws Exception {
		categoriesDAO.create(item);
	}

	@Override
	public boolean remove(CategoriesVO item) throws Exception {		
		return categoriesDAO.remove(item);
	}

	@Override
	public boolean update(CategoriesVO item) throws Exception {
		return categoriesDAO.update(item);
	}

	@Override
	public List<CategoriesVO> queryCategories() {
		return categoriesDAO.findAllCategories();
	}

	@Override
	public CategoriesVO queryCategoriesById(long id) {		
		return categoriesDAO.findCategoryById(id);
	}

	@Override
	public CategoriesVO queryCategoriesByName(String string) {
		CategoriesVO category = null; 
		List<CategoriesVO> queryCategories = this.queryCategories();
		for (CategoriesVO categoriesVO : queryCategories) {
			if (categoriesVO.getName().equals(string)) {
				category = categoriesVO;
			}
		}
		return category;
	}
	
	
}
