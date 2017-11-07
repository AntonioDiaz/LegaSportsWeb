package com.adiaz.daos;


import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.Center;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;

@Repository
public class CenterDAOImpl implements CenterDAO {

	@Override
	public Key<Center> create(Center item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(Center item) throws Exception {
		boolean updateResult = false;
		if (item != null && item.getId() != null) {
			Center c = ofy().load().type(Center.class).id(item.getId()).now();
			if (c != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			} 
		}
		return updateResult;
	}

	@Override
	public boolean remove(Center item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}
	
	@Override
	public boolean remove(Long id) throws Exception {
		ofy().delete().type(Center.class).id(id).now();
		return true;
	}

	@Override
	public List<Center> findAll() {
		return ofy().load().type(Center.class).list();
	}

	@Override
	public Center findById(Long id) {
		return ofy().load().type(Center.class).id(id).now();
	}


	@Override
	public List<Center> findByTown(Long idTown) {
		Key<Town> key = Key.create(Town.class, idTown);
		Ref<Town> townRef = Ref.create(key);
		Query<Center> query = ofy().load().type(Center.class).filter("townRef", townRef);
		return query.list();
	}
}
