package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.CategoriesVO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;


@Repository
public class CategoriesDAOImpl implements CategoriesDAO {

	//private static final Logger log = Logger.getLogger(CategoriesDAOImpl.class.getName());
	
	@Override
	public Key<CategoriesVO> create(CategoriesVO item) throws Exception {
		return ofy().save().entity(item).now();
	}
	
	@Override
	public boolean update(CategoriesVO item) throws Exception {
		boolean updateResult;
		if (item == null || item.getId() == null) {
			updateResult = false;
		} else {
			CategoriesVO c = ofy().load().type(CategoriesVO.class).id(item.getId()).now();
			if (c != null) {
				c.setName(item.getName()); 
				ofy().save().entity(c).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(CategoriesVO item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<CategoriesVO> findAllCategories() {
		Query<CategoriesVO> query = ofy().load().type(CategoriesVO.class);
		return query.order("order").list();	
	}
	
	@Override
	public CategoriesVO findCategoryById(Long id) {
		return ofy().load().type(CategoriesVO.class).id(id).now();		
	}
}
