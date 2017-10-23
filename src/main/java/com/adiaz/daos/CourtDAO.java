package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Center;
import com.adiaz.entities.Court;
import com.googlecode.objectify.Ref;

public interface CourtDAO extends GenericDAO<Court> {
	Ref<Court> createReturnRef(Court item) throws Exception;
	List<Court> findAll();

	Court findById(Long idCourt);

	// TODO: 20/09/2017 change method signature: centerRef will be Long, to keep unity with others daos.
	List<Court> findBySportCenter(Ref<Center> centerRef);
	List<Court> findBySport(Long idSport);
}
