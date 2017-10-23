package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Center;
import com.adiaz.entities.Court;
import com.googlecode.objectify.Ref;

public interface CourtDAO extends GenericDAO<Court> {
	Ref<Court> createReturnRef(Court item) throws Exception;
	List<Court> findAll();

	Court findById(Long idCourt);

	List<Court> findBySportCenter(Long idCenter);
	List<Court> findBySport(Long idSport);
}
