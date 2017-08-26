package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.ClassificationEntry;
import com.adiaz.entities.Competition;

public interface ClassificationEntriesDAO extends GenericDAO<ClassificationEntry> {
	public List<ClassificationEntry> findByCompetitionId(Long idCompetition);
	public ClassificationEntry findById(Long id);
	public List<ClassificationEntry> findAll();
	public void remove(Iterable<ClassificationEntry> listToDelete);
	public void save(Iterable<ClassificationEntry> listToUpdate);
}
