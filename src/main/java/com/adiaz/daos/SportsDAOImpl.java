package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.Sport;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Repository
public class SportsDAOImpl implements SportsDAO {

	@Override
	public Key<Sport> create(Sport sportsVO) throws Exception {
		return ofy().save().entity(sportsVO).now();
	}

	@Override
	public boolean update(Sport sport) throws Exception {
		boolean updateResult;
		if (sport == null || sport.getId() == null) {
			updateResult = false;
		} else {
			Sport c = ofy().load().type(Sport.class).id(sport.getId()).now();
			if (c != null) {
				ofy().save().entity(sport).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Sport sport) throws Exception {
		ofy().delete().entity(sport).now();
		return true;
	}

	public boolean remove(List<Sport> sportsList) throws Exception {
		ofy().delete().entity(sportsList).now();
		return true;
	}

	@Override
	public List<Sport> findAllSports() {
		Query<Sport> query = ofy().load().type(Sport.class);
		return query.order("name").list();	
	}
	
	@Override 
	public Sport findSportById(Long id) {
		Sport sport = ofy().load().type(Sport.class).id(id).now();
		return sport;
	}
}
