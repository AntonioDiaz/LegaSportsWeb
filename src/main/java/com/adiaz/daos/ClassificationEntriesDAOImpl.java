package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.Competition;
import org.springframework.stereotype.Repository;

import com.adiaz.entities.ClassificationEntry;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

@Repository
public class ClassificationEntriesDAOImpl implements ClassificationEntriesDAO {

	@Override
	public Key<ClassificationEntry> create(ClassificationEntry item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(ClassificationEntry item) throws Exception {
		boolean updateResult;
		if (item == null || item.getId() == null) {
			updateResult = false;
		} else {
			ClassificationEntry c = ofy().load().type(ClassificationEntry.class).id(item.getId()).now();
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
	public boolean remove(ClassificationEntry item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<ClassificationEntry> findByCompetition(Long idCompetition) {
		Query<ClassificationEntry> query = ofy().load().type(ClassificationEntry.class);
		List<ClassificationEntry> list = null;
		if (idCompetition!=null) {			
			Key<Competition> key = Key.create(Competition.class, idCompetition);
			list  = query.filter("competitionRef", Ref.create(key)).order("position").list();
		}
		return list;
	}

	@Override
	public ClassificationEntry findById(Long id) {
		return ofy().load().type(ClassificationEntry.class).id(id).now();
	}

	@Override
	public List<ClassificationEntry> findAll() {
		return ofy().load().type(ClassificationEntry.class).list();
	}

	@Override
	public void remove(Iterable<ClassificationEntry> listToDelete) {
		ofy().delete().entities(listToDelete).now();
	}

	@Override
	public void save(Iterable<ClassificationEntry> listToUpdate) {
		ofy().save().entities(listToUpdate).now();
	}
}
