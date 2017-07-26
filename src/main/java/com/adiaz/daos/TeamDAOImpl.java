package com.adiaz.daos;

import com.adiaz.entities.Category;
import com.adiaz.entities.Club;
import com.adiaz.entities.Team;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by toni on 25/07/2017.
 */
@Repository
public class TeamDAOImpl implements TeamDAO {

	@Override
	public Key<Team> create(Team team) throws Exception {
		return ofy().save().entity(team).now();
	}

	@Override
	public boolean update(Team team) throws Exception {
		boolean updateResult = false;
		if (team != null && team.getId() != null) {
			Team t = ofy().load().type(Team.class).id(team.getId()).now();
			if (t != null) {
				ofy().save().entity(team).now();
				updateResult = true;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Team team) throws Exception {
		ofy().delete().entity(team).now();
		return true;
	}

	@Override
	public List<Team> findAll() {
		return ofy().load().type(Team.class).order("name").list();
	}

	@Override
	public Team findById(Long id) {
		return ofy().load().type(Team.class).id(id).now();
	}

	@Override
	public List<Team> find(Long townId, Long categoryId) {
		Query<Team> query = ofy().load().type(Team.class);
		if (townId!=null) {
			Key<Town> key = Key.create(Town.class, townId);
			query = query.filter("townRef", Ref.create(key));
		}
		if (categoryId!=null) {
			Key<Category> key = Key.create(Category.class, categoryId);
			query = query.filter("categoryRef", Ref.create(key));
		}
		return query.order("name").list();
	}
}
