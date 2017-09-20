package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.SportCenter;


public interface SportCenterDAO extends GenericDAO<SportCenter> {
	List<SportCenter> findAll();
	SportCenter findById(Long id);
	List<SportCenter> findByTown(Long idTown);
	boolean remove(Long id) throws Exception;
}
