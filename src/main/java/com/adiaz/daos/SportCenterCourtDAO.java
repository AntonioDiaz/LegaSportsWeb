package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Sport;
import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCenterCourt;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Ref;

public interface SportCenterCourtDAO extends GenericDAO<SportCenterCourt> {
	Ref<SportCenterCourt> createReturnRef(SportCenterCourt item) throws Exception;
	List<SportCenterCourt> findAll();

	// TODO: 20/09/2017 rename method findBySportCenter to findById
	SportCenterCourt findBySportCenter(Long idCourt);

	// TODO: 20/09/2017 change method signature: sportCenterRef will be Long, to keep unity with others daos.
	List<SportCenterCourt> findBySportCenter(Ref<SportCenter> sportCenterRef);
	List<SportCenterCourt> findBySport(Long idSport);
}
