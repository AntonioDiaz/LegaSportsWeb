package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.*;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Repository
public class CompetitionsDAOImpl implements CompetitionsDAO {

	@Override
	public Key<Competition> create(Competition competition) throws Exception {
		return ofy().save().entity(competition).now();
	}

	@Override
	public boolean update(Competition competition) throws Exception {
		boolean updateResult = false;
		if (competition != null && competition.getId() != null) {
			updateResult = true;
			ofy().save().entity(competition).now();
		}
		return updateResult;
	}

	@Override
	public boolean remove(Competition item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<Competition> findAll() {
		Query<Competition> query = ofy().load().type(Competition.class);
		List<Competition> list = query.list();
		return list;
	}
	
	@Override
	public List<Competition> findBySport(Long idSport) {
		return  find(idSport, null, null);
	}

	@Override
	public List<Competition> findByCategory(Long idCategory) {
		return find(null, idCategory, null);
	}

	@Override
	public List<Competition> find(Long idTown, boolean onlyPublished) {
		Query<Competition> query = ofy().load().type(Competition.class);
		Key<Town> key = Key.create(Town.class, idTown);
		query = query.filter("townRef", key);
		if (onlyPublished) {
			query = query.filter("visible", Boolean.TRUE);
		}
		return query.list();
	}

	@Override
	public List<Competition> findByTown(Long idTown) {
		return find(null, null, idTown);
	}

	@Override
	public List<Competition> findByTeam(Long idTeam) {
		Ref<Team> teamRef = Ref.create(Key.create(Team.class, idTeam));
		return ofy().load().type(Competition.class).filter("teams", teamRef).list();
	}

	@Override
	public List<Competition> find(Long idSport, Long idCategory, Long idTown) {
		Query<Competition> query = ofy().load().type(Competition.class);
		if (idSport!=null) {
			Key<Sport> key = Key.create(Sport.class, idSport);
			query = query.filter("sportRef", key);
		}
		if (idCategory!=null) {
			Key<Category> key = Key.create(Category.class, idCategory);
			query = query.filter("categoryRef", key);
		}
		if (idTown!=null) {
			Key<Town> key = Key.create(Town.class, idTown);
			query = query.filter("townRef", key);
		}
		return query.list();
	}
	
	@Override
	public Competition findById(Long id) {
		return ofy().load().type(Competition.class).id(id).now();
	}
}
