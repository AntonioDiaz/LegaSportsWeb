package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.SportCenter;


public interface SportCenterDAO extends GenericDAO<SportCenter> {
	List<SportCenter> findAllSportsCenters();
	SportCenter findSportsCenterById(Long id);
	List<SportCenter> findSportsCenterByTown(Long idTown);
	boolean remove(Long id) throws Exception;
}
