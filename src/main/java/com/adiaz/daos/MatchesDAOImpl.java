package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Court;
import org.springframework.stereotype.Repository;

import com.adiaz.entities.Match;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

@Repository
public class MatchesDAOImpl implements MatchesDAO {

	@Override
	public Key<Match> create(Match item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(Match item) throws Exception {
		boolean updateResult;
		if (item == null || item.getId() == null) {
			updateResult = false;
		} else {
			Match c = ofy().load().type(Match.class).id(item.getId()).now();
			if (c != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Match item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<Match> findByCompetition(Long competitionId) {
		return findByCompetition(competitionId, false);
	}

	@Override
	public List<Match> findByCourt(Long idCourt) {
		Ref<Court> ref = Ref.create(Key.create(Court.class, idCourt));
		return ofy().load().type(Match.class).filter("courtRef", ref).list();
	}

	@Override
	public List<Match> findByCompetition(Long competitionId, boolean workingCopy) {
		List<Match> matches = null;
		Query<Match> query = ofy().load().type(Match.class);
		if (competitionId!=null) {
			Key<Competition> key = Key.create(Competition.class, competitionId);
			matches = query
					.filter("competitionRef", Ref.create(key))
					.filter("workingCopy", workingCopy)
					.order("week").order("date").list();
		}
		return matches;
	}

	@Override
	public List<Match> findAll() {
		Query<Match> query = ofy().load().type(Match.class);
		return query.list();
	}

	@Override
	public Match findById(Long id) {
		return ofy().load().type(Match.class).id(id).now();
	}

	@Override
	public void remove(Iterable<Match> listToDelete) {
		ofy().delete().entities(listToDelete).now();
	}
}
