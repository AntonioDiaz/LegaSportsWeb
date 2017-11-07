package com.adiaz.daos;

import com.adiaz.entities.Sport;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by toni on 14/07/2017.
 */
@Repository
public class TownDAOImpl implements TownDAO {

	@Override
	public Key<Town> create(Town item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(Town item) throws Exception {
		boolean updateResult = false;
		if (item != null && item.getId() != null) {
			Town t = ofy().load().type(Town.class).id(item.getId()).now();
			if (t != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Town item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<Town> findAll() {
		return ofy().load().type(Town.class).order("name").list();
	}

	@Override
	public Town findById(Long id) {
		return ofy().load().type(Town.class).id(id).now();
	}

	@Override
	public List<Town> findByName(String name) {
		return ofy().load().type(Town.class).filter("name", name).list();
	}

	@Override
	public List<Town> findBySport(Long idSport) {
		Ref<Sport> sportRef = Ref.create(Key.create(Sport.class, idSport));
		return ofy().load().type(Town.class).filter("sports", sportRef).list();
	}
}
