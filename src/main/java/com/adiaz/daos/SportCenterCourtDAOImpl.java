package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.Sport;
import com.adiaz.entities.SportCenterCourt;
import com.adiaz.entities.Town;
import org.springframework.stereotype.Repository;

import com.adiaz.entities.SportCenter;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;


@Repository
public class SportCenterCourtDAOImpl implements SportCenterCourtDAO {
	
	@Override
	public Key<SportCenterCourt> create(SportCenterCourt item) throws Exception {
		return ofy().save().entity(item).now();
	
	}
	@Override
	public Ref<SportCenterCourt> createReturnRef(SportCenterCourt item) throws Exception {
		Key<SportCenterCourt> myKey = ofy().save().entity(item).now();
		Ref<SportCenterCourt> myRef = Ref.create(myKey);
		return myRef;
	}

	@Override
	public boolean update(SportCenterCourt item) throws Exception {
		boolean updateResult = false;
		if (item != null && item.getId() != null) {
			SportCenterCourt c = ofy().load().type(SportCenterCourt.class).id(item.getId()).now();
			if (c != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			} 
		}
		return updateResult;
	}

	@Override
	public boolean remove(SportCenterCourt item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<SportCenterCourt> findAll() {
		return ofy().load().type(SportCenterCourt.class).list();

	}
	
	@Override
	public SportCenterCourt findBySportCenter(Long idCourt) {
		Key<SportCenterCourt> key = Key.create(SportCenterCourt.class, idCourt);
		return ofy().load().key(key).now();
	}
	@Override
	public List<SportCenterCourt> findBySportCenter(Ref<SportCenter> sportCenterRef) {
		return ofy().load().type(SportCenterCourt.class).filter("sportCenterRef", sportCenterRef).list();
	}

	@Override
	public List<SportCenterCourt> findBySport(Long idSport) {
		Ref<Sport> sportRef = Ref.create(Key.create(Sport.class, idSport));
		return ofy().load().type(SportCenterCourt.class).filter("sports", sportRef).list();
	}
}
