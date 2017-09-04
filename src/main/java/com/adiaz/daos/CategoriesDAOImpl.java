package com.adiaz.daos;

import com.adiaz.entities.Category;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;


@Repository
public class CategoriesDAOImpl implements CategoriesDAO {

	//private static final Logger log = Logger.getLogger(CategoriesDAOImpl.class.getName());
	
	@Override
	public Key<Category> create(Category item) throws Exception {
		return ofy().save().entity(item).now();
	}
	
	@Override
	public boolean update(Category item) throws Exception {
		boolean updateResult;
		if (item == null || item.getId() == null) {
			updateResult = false;
		} else {
			Category c = ofy().load().type(Category.class).id(item.getId()).now();
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
	public boolean remove(Category item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<Category> findAllCategories() {
		Query<Category> query = ofy().load().type(Category.class);
		return query.order("order").list();	
	}
	
	@Override
	public Category findCategoryById(Long id) {
		return ofy().load().type(Category.class).id(id).now();
	}

}
