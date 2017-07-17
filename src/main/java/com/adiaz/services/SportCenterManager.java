package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCenter;
import com.adiaz.forms.SportCenterForm;

public interface SportCenterManager {
	public void addSportCenter(SportCenterForm sportCenterForm) throws Exception;
	public boolean updateSportCenter(SportCenterForm sportCenterForm) throws Exception;
	public boolean removeSportCenter(Long id) throws Exception;
	public List<SportCenter> querySportCenters();
	public SportCenterForm querySportCentersById(Long id);
	public void removeAll() throws Exception;
}
