package com.adiaz.daos;


import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.SportCenter;
import com.googlecode.objectify.Key;

@Repository
public class SportCenterDAOImpl implements SportCenterDAO {

	@Override
	public Key<SportCenter> create(SportCenter item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(SportCenter item) throws Exception {
		boolean updateResult = false;
		if (item != null && item.getId() != null) {
			SportCenter c = ofy().load().type(SportCenter.class).id(item.getId()).now();
			if (c != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			} 
		}
		return updateResult;
	}

	@Override
	public boolean remove(SportCenter item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}
	
	@Override
	public boolean remove(Long id) throws Exception {
		ofy().delete().type(SportCenter.class).id(id).now();
		return true;
	}

	@Override
	public List<SportCenter> findAllSportsCenters() {
		return ofy().load().type(SportCenter.class).list();
	}

	@Override
	public SportCenter findSportsCenterById(Long id) {
		return ofy().load().type(SportCenter.class).id(id).now();
	}
	
	
}
