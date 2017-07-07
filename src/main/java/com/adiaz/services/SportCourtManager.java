package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCourt;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Ref;

public interface SportCourtManager {

	public Ref<SportCourt> addSportCourt(SportCourt sportCourt) throws Exception;
	public Ref<SportCourt> addSportCourt(SportsCourtForm sportCourtForm) throws Exception;
	public boolean updateSportCourt(SportCourt sportCourt) throws Exception;
	public boolean removeSportCourt(Long idCourt) throws Exception;
	public List<SportCourt> querySportCourt();
	public SportCourt querySportCourt(Long idCourt);
	public void removeAll() throws Exception;
	public List<SportCourt> querySportCourts(Long idSportCenter);
	public void updateSportCourt(SportsCourtForm sportsCourtForm) throws Exception;

}
