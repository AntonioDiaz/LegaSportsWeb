package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.Competition;
import org.springframework.stereotype.Repository;

import com.adiaz.entities.Category;
import com.adiaz.entities.Sport;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Repository
public class CompetitionsDAOImpl implements CompetitionsDAO {

	@Override
	public Key<Competition> create(Competition item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(Competition competitionVO) throws Exception {
		boolean updateResult = false;
		if (competitionVO != null && competitionVO.getId() != null) {
			updateResult = true;
			ofy().save().entity(competitionVO).now();
		}
		return updateResult;
	}

	@Override
	public boolean remove(Competition item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<Competition> findCompetitions() {
		Query<Competition> query = ofy().load().type(Competition.class);
		List<Competition> list = query.list();
		return list;
	}
	
	@Override
	public List<Competition> findCompetitionsBySport(Sport sport) {
		Query<Competition> query = ofy().load().type(Competition.class);
		List<Competition> list = null;
		if (sport !=null) {
			Key<Sport> key = Key.create(Sport.class, sport.getId());
			query.ancestor(key);
			list  = query.list();
		}
		return list;
	}

	@Override
	public List<Competition> findCompetitions(Long sportId, Long categoryId) {
		Query<Competition> query = ofy().load().type(Competition.class);
		if (sportId!=null) {
			Key<Sport> key = Key.create(Sport.class, sportId);
			query = query.filter("sport", key);
		}
		if (categoryId!=null) {
			Key<Category> key = Key.create(Category.class, categoryId);
			query = query.filter("category", key);
		}
		return query.list();
	}
	
	@Override
	public Competition findCompetitionsById(Long id) {
		return ofy().load().type(Competition.class).id(id).now();
	}
}
