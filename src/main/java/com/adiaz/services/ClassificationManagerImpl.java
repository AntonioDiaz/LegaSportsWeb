package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.ClassificationEntriesDAO;
import com.adiaz.entities.ClassificationEntry;

@Service ("classificationManager")
public class ClassificationManagerImpl implements ClassificationManager {

	@Autowired ClassificationEntriesDAO classificationEntriesDAO;
	
	@Override
	public void add(ClassificationEntry item) throws Exception {
		classificationEntriesDAO.create(item);

	}

	@Override
	public boolean remove(ClassificationEntry item) throws Exception {
		return classificationEntriesDAO.remove(item);
	}

	@Override
	public boolean update(ClassificationEntry item) throws Exception {
		return classificationEntriesDAO.update(item);
	}

	@Override
	public List<ClassificationEntry> queryClassificationByCompetition(Long idCompetition) {
		return classificationEntriesDAO.findByCompetitionId(idCompetition);
	}

	@Override
	public void add(List<ClassificationEntry> classificationList) throws Exception {
		for (ClassificationEntry classificationEntry : classificationList) {
			this.add(classificationEntry);
		}
	}

	@Override
	public void removeAll() throws Exception {
		List<ClassificationEntry> list = classificationEntriesDAO.findAll();
		for (ClassificationEntry classificationEntry : list) {
			classificationEntriesDAO.remove(classificationEntry);
		}
	}
}
