package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.SportVO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Repository
public class SportsDAOImpl implements SportsDAO {

	@Override
	public Key<SportVO> create(SportVO sportsVO) throws Exception {
		return ofy().save().entity(sportsVO).now();
	}

	@Override
	public boolean update(SportVO sportVO) throws Exception {
		boolean updateResult;
		if (sportVO == null || sportVO.getId() == null) {
			updateResult = false;
		} else {
			SportVO c = ofy().load().type(SportVO.class).id(sportVO.getId()).now();
			if (c != null) {
				ofy().save().entity(sportVO).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(SportVO sportVO) throws Exception {
		ofy().delete().entity(sportVO).now();
		return true;
	}

	@Override
	public List<SportVO> findAllSports() {
		Query<SportVO> query = ofy().load().type(SportVO.class);
		return query.order("name").list();	
	}
	
	@Override 
	public SportVO findSportById(Long id) {
		SportVO sportVO = ofy().load().type(SportVO.class).id(id).now();
		return sportVO;
	}
}
