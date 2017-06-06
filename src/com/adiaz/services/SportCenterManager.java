package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCenter;

public interface SportCenterManager {

	public void addSportCenter(SportCenter sportCenter) throws Exception;
	public boolean updateSportCenter(SportCenter sportCenter) throws Exception;
	public boolean removeSportCenter(SportCenter sportCenter) throws Exception;
	public boolean removeSportCenter(Long id) throws Exception;
	public List<SportCenter> querySportCenters();
	public SportCenter querySportCentersById(Long id);
	public void removeAll() throws Exception;
	//public boolean addCourtToCenter(SportsCourtForm sportsCourtForm) throws Exception;	
}
