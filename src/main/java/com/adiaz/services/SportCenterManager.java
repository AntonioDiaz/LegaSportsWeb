package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCenter;
import com.adiaz.forms.SportCenterForm;
import com.googlecode.objectify.Key;

public interface SportCenterManager {
	void addSportCenter(SportCenterForm sportCenterForm) throws Exception;
	Key<SportCenter> addSportCenter(SportCenter sportCenter) throws Exception;
	boolean updateSportCenter(SportCenterForm sportCenterForm) throws Exception;
	boolean removeSportCenter(Long id) throws Exception;
	List<SportCenter> querySportCenters();
	List<SportCenter> querySportCenters(Long idTown);
	SportCenterForm querySportCentersById(Long id);
	void removeAll() throws Exception;
}
