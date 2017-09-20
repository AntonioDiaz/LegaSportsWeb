package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.Sport;
import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCenterCourt;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Ref;

public interface SportCenterCourtDAO extends GenericDAO<SportCenterCourt> {
	Ref<SportCenterCourt> createReturnRef(SportCenterCourt item) throws Exception;
	List<SportCenterCourt> findAllSportCourt();
	SportCenterCourt findBySportCenter(Long idCourt);
	List<SportCenterCourt> findBySportCenter(Ref<SportCenter> sportCenterRef);
	List<SportCenterCourt> findBySport(Long idSport);
}
