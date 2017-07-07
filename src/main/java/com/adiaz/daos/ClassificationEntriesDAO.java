package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.ClassificationEntryVO;

public interface ClassificationEntriesDAO extends GenericDAO<ClassificationEntryVO> {
	
	public List<ClassificationEntryVO> findClassification(Long idCompetition);
	public List<ClassificationEntryVO> findAll();
	
}
