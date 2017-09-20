package com.adiaz.daos;

import com.adiaz.entities.Club;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.Queue;

/**
 * Created by toni on 24/07/2017.
 */
@Repository
public class ClubDAOImpl implements ClubDAO {

	@Override
	public Key<Club> create(Club club) throws Exception {
		return ofy().save().entity(club).now();
	}

	@Override
	public boolean update(Club club) throws Exception {
		boolean updateResult = false;
		if (club != null && club.getId() != null) {
			updateResult = true;
			ofy().save().entity(club).now();
		}
		return updateResult;

	}

	@Override
	public boolean remove(Club club) throws Exception {
		ofy().delete().entity(club).now();
		return true;
	}

	@Override
	public Club findById(Long id) {
		return ofy().load().type(Club.class).id(id).now();
	}

	@Override
	public List<Club> findAll() {
		return ofy().load().type(Club.class).list();
	}

	@Override
	public List<Club> findByTown(Long townId) {
		Query<Club> query = ofy().load().type(Club.class);
		if (townId!=null) {
			Key<Town> key = Key.create(Town.class, townId);
			query = query.filter("townRef", Ref.create(key));
		}
		return query.list();
	}
}
