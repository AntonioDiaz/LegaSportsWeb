package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.Court;
import com.adiaz.entities.Sport;
import org.springframework.stereotype.Repository;

import com.adiaz.entities.Center;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;


@Repository
public class CourtDAOImpl implements CourtDAO {
	
	@Override
	public Key<Court> create(Court item) throws Exception {
		return ofy().save().entity(item).now();
	
	}
	@Override
	public Ref<Court> createReturnRef(Court item) throws Exception {
		Key<Court> myKey = ofy().save().entity(item).now();
		Ref<Court> myRef = Ref.create(myKey);
		return myRef;
	}

	@Override
	public boolean update(Court item) throws Exception {
		boolean updateResult = false;
		if (item != null && item.getId() != null) {
			Court c = ofy().load().type(Court.class).id(item.getId()).now();
			if (c != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			} 
		}
		return updateResult;
	}

	@Override
	public boolean remove(Court item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<Court> findAll() {
		return ofy().load().type(Court.class).list();

	}
	
	@Override
	public Court findBySportCenter(Long idCourt) {
		Key<Court> key = Key.create(Court.class, idCourt);
		return ofy().load().key(key).now();
	}
	@Override
	public List<Court> findBySportCenter(Ref<Center> centerRef) {
		return ofy().load().type(Court.class).filter("centerRef", centerRef).list();
	}

	@Override
	public List<Court> findBySport(Long idSport) {
		Ref<Sport> sportRef = Ref.create(Key.create(Sport.class, idSport));
		return ofy().load().type(Court.class).filter("sports", sportRef).list();
	}
}
