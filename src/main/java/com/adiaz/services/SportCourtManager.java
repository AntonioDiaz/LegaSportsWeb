package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCenterCourt;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Ref;

public interface SportCourtManager {

	public Ref<SportCenterCourt> addSportCourt(SportCenterCourt sportCenterCourt) throws Exception;
	public Ref<SportCenterCourt> addSportCourt(SportsCourtForm sportCourtForm) throws Exception;
	public boolean updateSportCourt(SportCenterCourt sportCenterCourt) throws Exception;
	public boolean removeSportCourt(Long idCourt) throws Exception;
	public List<SportCenterCourt> querySportCourt();
	public SportCenterCourt querySportCourt(Long idCourt);
	public void removeAll() throws Exception;
	public List<SportCenterCourt> querySportCourts(Long idSportCenter);
	public void updateSportCourt(SportsCourtForm sportsCourtForm) throws Exception;

}
