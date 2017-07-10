package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

@Repository
public class MatchesDAOImpl implements MatchesDAO {

	@Override
	public Key<MatchesVO> create(MatchesVO item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(MatchesVO item) throws Exception {
		boolean updateResult;
		if (item == null || item.getId() == null) {
			updateResult = false;
		} else {
			MatchesVO c = ofy().load().type(MatchesVO.class).id(item.getId()).now();
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
	public boolean remove(MatchesVO item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<MatchesVO> queryMatchesByCompetition(Long competitionId) {
		List<MatchesVO> matches = null;
		Query<MatchesVO> query = ofy().load().type(MatchesVO.class);
		if (competitionId!=null) {
			Key<CompetitionsVO> key = Key.create(CompetitionsVO.class, competitionId);
			matches = query.filter("competitionRef", Ref.create(key)).order("week").order("date").list();
		}
		return matches;
	}

	@Override
	public List<MatchesVO> queryAllMatches() {
		Query<MatchesVO> query = ofy().load().type(MatchesVO.class);
		return query.list();
	}

	@Override
	public MatchesVO queryMatches(Long id) {
		return ofy().load().type(MatchesVO.class).id(id).now();
	}
}
