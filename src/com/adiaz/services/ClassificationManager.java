package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.ClassificationEntryVO;

public interface ClassificationManager {

	public void add(ClassificationEntryVO item) throws Exception;	
	public boolean remove(ClassificationEntryVO item) throws Exception;	
	public boolean update(ClassificationEntryVO item) throws Exception;
	public List<ClassificationEntryVO> queryClassificationBySport(Long idCompetition);
	public void add(List<ClassificationEntryVO> classificationList) throws Exception;

}
