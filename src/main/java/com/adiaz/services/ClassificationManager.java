package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.ClassificationEntry;

public interface ClassificationManager {

	public void add(ClassificationEntry item) throws Exception;
	public boolean remove(ClassificationEntry item) throws Exception;
	public void removeAll() throws Exception;
	public boolean update(ClassificationEntry item) throws Exception;
	public List<ClassificationEntry> queryClassificationByCompetition(Long idCompetition);
	public void add(List<ClassificationEntry> classificationList) throws Exception;

	public void updateClassificationByCompetition(Long idCompetition);
	public void initClassification(Long idCompetition);


	}
