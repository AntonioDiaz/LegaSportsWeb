package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.SportVO;

public interface SportsDAO extends GenericDAO<SportVO>{
	public List<SportVO> findAllSports();
	public SportVO findSportById(Long id);	
}
