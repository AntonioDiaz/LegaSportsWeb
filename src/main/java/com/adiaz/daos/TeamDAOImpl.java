package com.adiaz.daos;

import com.adiaz.entities.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
	public List<Team> find(Long townId, Long categoryId, Long sportId, String name) {
		Query<Team> query = ofy().load().type(Team.class);
		if (townId!=null) {
			Key<Town> key = Key.create(Town.class, townId);
			query = query.filter("townRef", Ref.create(key));
		}
		if (categoryId!=null) {
			Key<Category> key = Key.create(Category.class, categoryId);
			query = query.filter("categoryRef", Ref.create(key));
		}
		if (sportId!=null) {
			Key<Sport> key = Key.create(Sport.class, sportId);
			query = query.filter("sportRef", Ref.create(key));
		}
		if (name!=null) {
			query = query.filter("name", name);
		}
		return query.order("name").list();
	}

	@Override
	public List<Team> findBySport(Long idSport) {
		return find(null, null, idSport, null);
	}

	@Override
	public List<Team> find(Long townId, Long categoryId, Long sportId) {
		return  find(townId, categoryId, sportId, null);
	}

	@Override
	public List<Team> findByCategory(Long idCategory) {
		return find(null, idCategory, null, null);
	}

	@Override
	public List<Team> findByTown(Long idTown) {
		return find(idTown, null, null, null);
	}

	@Override
	public List<Team> findByClub(Long idClub) {
		Key<Club> clubKey = Key.create(Club.class, idClub);
		Ref<Club> clubRef = Ref.create(clubKey);
		return ofy().load().type(Team.class).filter("clubRef", clubRef).list();
	}

	@Override
	public Map<Key<Team>, Team> create(List<Team> teamList) {
		Map<Key<Team>, Team> teamMap = ofy().save().entities(teamList).now();
		return  teamMap;
	}
}
