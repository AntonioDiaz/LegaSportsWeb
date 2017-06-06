package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.ClassificationEntriesDAO;
import com.adiaz.entities.ClassificationEntryVO;

@Service ("classificationManager")
public class ClassificationManagerImpl implements ClassificationManager {

	@Autowired ClassificationEntriesDAO classificationEntriesDAO;
	
	@Override
	public void add(ClassificationEntryVO item) throws Exception {
		classificationEntriesDAO.create(item);

	}

	@Override
	public boolean remove(ClassificationEntryVO item) throws Exception {		
		return classificationEntriesDAO.remove(item);
	}

	@Override
	public boolean update(ClassificationEntryVO item) throws Exception {
		return classificationEntriesDAO.update(item);
	}

	@Override
	public List<ClassificationEntryVO> queryClassificationBySport(Long idCompetition) {		
		return classificationEntriesDAO.findClassification(idCompetition);
	}

	@Override
	public void add(List<ClassificationEntryVO> classificationList) throws Exception {
		for (ClassificationEntryVO classificationEntryVO : classificationList) {
			this.add(classificationEntryVO);
		}
	}

	@Override
	public void removeAll() throws Exception {
		List<ClassificationEntryVO> list = classificationEntriesDAO.findAll();
		for (ClassificationEntryVO classificationEntryVO : list) {
			classificationEntriesDAO.remove(classificationEntryVO);
		}
		
	}
}
