package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.ClassificationEntry;
import com.adiaz.entities.Competition;

public interface ClassificationEntriesDAO extends GenericDAO<ClassificationEntry> {
	List<ClassificationEntry> findByCompetitionId(Long idCompetition);
	ClassificationEntry findById(Long id);
	List<ClassificationEntry> findAll();
	void remove(Iterable<ClassificationEntry> listToDelete);
	void save(Iterable<ClassificationEntry> listToUpdate);
}
