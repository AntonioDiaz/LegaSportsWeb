package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.ClassificationEntry;

public interface ClassificationEntriesDAO extends GenericDAO<ClassificationEntry> {
	
	public List<ClassificationEntry> findByCompetitionId(Long idCompetition);
	public ClassificationEntry findById(Long idCompetition);
	public List<ClassificationEntry> findAll();
	
}
