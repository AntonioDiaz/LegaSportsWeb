package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCenter;
import com.adiaz.forms.SportCenterForm;
import com.googlecode.objectify.Key;

public interface SportCenterManager {
	public void addSportCenter(SportCenterForm sportCenterForm) throws Exception;
	public Key<SportCenter> addSportCenter(SportCenter sportCenter) throws Exception;
	public boolean updateSportCenter(SportCenterForm sportCenterForm) throws Exception;
	public boolean removeSportCenter(Long id) throws Exception;
	public List<SportCenter> querySportCenters();
	public List<SportCenter> querySportCenters(Long idTown);
	public SportCenterForm querySportCentersById(Long id);
	public void removeAll() throws Exception;
}
