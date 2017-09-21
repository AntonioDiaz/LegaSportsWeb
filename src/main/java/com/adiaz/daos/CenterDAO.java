package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Center;


public interface CenterDAO extends GenericDAO<Center> {
	List<Center> findAll();
	Center findById(Long id);
	List<Center> findByTown(Long idTown);
	boolean remove(Long id) throws Exception;
}
