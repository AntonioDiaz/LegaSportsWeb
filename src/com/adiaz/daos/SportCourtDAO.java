package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.SportCourt;
import com.googlecode.objectify.Ref;

public interface SportCourtDAO extends GenericDAO<SportCourt> {
	
	public Ref<SportCourt> createReturnRef(SportCourt item) throws Exception;
	public List<SportCourt> findAllSportCourt();
}