package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCenterCourt;
import com.googlecode.objectify.Ref;

public interface SportCourtDAO extends GenericDAO<SportCenterCourt> {
	
	public Ref<SportCenterCourt> createReturnRef(SportCenterCourt item) throws Exception;
	public List<SportCenterCourt> findAllSportCourt();
	public SportCenterCourt findSportCourt(Long idCourt);
	public List<SportCenterCourt> findSportCourt(Ref<SportCenter> sportCenterRef);
}
