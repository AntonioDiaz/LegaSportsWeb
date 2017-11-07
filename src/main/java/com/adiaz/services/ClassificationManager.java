package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.ClassificationEntry;

public interface ClassificationManager {
	void add(ClassificationEntry item) throws Exception;
	boolean remove(ClassificationEntry item) throws Exception;
	void removeByCompetition(Long idCompetition) throws Exception;
	void removeAll() throws Exception;
	boolean update(ClassificationEntry item) throws Exception;
	List<ClassificationEntry> queryClassificationByCompetition(Long idCompetition);
	void add(List<ClassificationEntry> classificationList) throws Exception;
	void updateClassificationByCompetition(Long idCompetition);
	void initClassification(Long idCompetition) throws Exception;
}
