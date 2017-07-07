package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.SportCenter;


public interface SportCenterDAO extends GenericDAO<SportCenter> {
	public List<SportCenter> findAllSportsCenters();
	public SportCenter findSportsCenterById(Long id);
	public boolean remove(Long id) throws Exception;
}
