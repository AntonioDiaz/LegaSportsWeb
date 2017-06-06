package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCourt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;


@Repository
public class SportCourtDAOImpl implements SportCourtDAO {

	
	@Override
	public Key<SportCourt> create(SportCourt item) throws Exception {
		return ofy().save().entity(item).now();
	
	}
	@Override
	public Ref<SportCourt> createReturnRef(SportCourt item) throws Exception {
		Key<SportCourt> myKey = ofy().save().entity(item).now();
		Ref<SportCourt> myRef = Ref.create(myKey);
		return myRef;
	}

	@Override
	public boolean update(SportCourt item) throws Exception {
		boolean updateResult = false;
		if (item != null && item.getId() != null) {
			SportCourt c = ofy().load().type(SportCourt.class).id(item.getId()).now();
			if (c != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			} 
		}
		return updateResult;
	}

	@Override
	public boolean remove(SportCourt item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<SportCourt> findAllSportCourt() {
		return ofy().load().type(SportCourt.class).list();

	}
	
	@Override
	public SportCourt findSportCourt(Long idCourt) {
		Key<SportCourt> key = Key.create(SportCourt.class, idCourt);		
		return ofy().load().key(key).now();
	}
	@Override
	public List<SportCourt> findSportCourt(Ref<SportCenter> sportCenterRef) {
		return ofy().load().type(SportCourt.class).filter("center", sportCenterRef).list();
	}
}
