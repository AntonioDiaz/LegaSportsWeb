package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.ClassificationEntryVO;

public interface ClassificationEntriesDAO extends GenericDAO<ClassificationEntryVO> {
	
	public List<ClassificationEntryVO> findByCompetitionId(Long idCompetition);
	public ClassificationEntryVO findById(Long idCompetition);
	public List<ClassificationEntryVO> findAll();
	
}
