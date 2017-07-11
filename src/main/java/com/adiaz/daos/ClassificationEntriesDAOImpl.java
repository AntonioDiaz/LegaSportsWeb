package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.ClassificationEntryVO;
import com.adiaz.entities.CompetitionsVO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

@Repository
public class ClassificationEntriesDAOImpl implements ClassificationEntriesDAO {

	@Override
	public Key<ClassificationEntryVO> create(ClassificationEntryVO item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(ClassificationEntryVO item) throws Exception {
		boolean updateResult;
		if (item == null || item.getId() == null) {
			updateResult = false;
		} else {
			ClassificationEntryVO c = ofy().load().type(ClassificationEntryVO.class).id(item.getId()).now();
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
	public boolean remove(ClassificationEntryVO item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<ClassificationEntryVO> findByCompetitionId(Long idCompetition) {
		Query<ClassificationEntryVO> query = ofy().load().type(ClassificationEntryVO.class);
		List<ClassificationEntryVO> list = null;
		if (idCompetition!=null) {			
			Key<CompetitionsVO> key = Key.create(CompetitionsVO.class, idCompetition);
			list  = query.filter("competitionRef", Ref.create(key)).order("position").list();
		}
		return list;
	}

	@Override
	public ClassificationEntryVO findById(Long id) {
		return ofy().load().type(ClassificationEntryVO.class).id(id).now();
	}

	@Override
	public List<ClassificationEntryVO> findAll() {
		return ofy().load().type(ClassificationEntryVO.class).list();
	}
}
