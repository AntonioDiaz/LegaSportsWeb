package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.Category;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.SportVO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Repository
public class CompetitionsDAOImpl implements CompetitionsDAO {

	@Override
	public Key<CompetitionsVO> create(CompetitionsVO item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(CompetitionsVO competitionVO) throws Exception {
		boolean updateResult = false;
		if (competitionVO != null && competitionVO.getId() != null) {
			updateResult = true;
			ofy().save().entity(competitionVO).now();
		}
		return updateResult;
	}

	@Override
	public boolean remove(CompetitionsVO item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<CompetitionsVO> findCompetitions() {
		Query<CompetitionsVO> query = ofy().load().type(CompetitionsVO.class);
		List<CompetitionsVO> list = query.list();
		return list;
	}
	
	@Override
	public List<CompetitionsVO> findCompetitionsBySport(SportVO sportVO) {
		Query<CompetitionsVO> query = ofy().load().type(CompetitionsVO.class);
		List<CompetitionsVO> list = null;
		if (sportVO!=null) {
			Key<SportVO> key = Key.create(SportVO.class, sportVO.getId());
			query.ancestor(key);
			list  = query.list();
		}
		return list;
	}

	@Override
	public List<CompetitionsVO> findCompetitions(Long sportId, Long categoryId) {
		Query<CompetitionsVO> query = ofy().load().type(CompetitionsVO.class);
		if (sportId!=null) {
			Key<SportVO> key = Key.create(SportVO.class, sportId);
			query = query.filter("sport", key);
		}
		if (categoryId!=null) {
			Key<Category> key = Key.create(Category.class, categoryId);
			query = query.filter("category", key);
		}
		return query.list();
	}
	
	@Override
	public CompetitionsVO findCompetitionsById(Long id) {
		return ofy().load().type(CompetitionsVO.class).id(id).now();
	}
}
