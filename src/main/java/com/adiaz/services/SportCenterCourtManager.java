package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Sport;
import com.adiaz.entities.SportCenterCourt;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Ref;

public interface SportCenterCourtManager {
	Ref<SportCenterCourt> addSportCourt(SportCenterCourt sportCenterCourt) throws Exception;
	Ref<SportCenterCourt> addSportCourt(SportsCourtForm sportCourtForm) throws Exception;
	boolean updateSportCourt(SportCenterCourt sportCenterCourt) throws Exception;
	boolean removeSportCourt(Long idCourt) throws Exception;
	List<SportCenterCourt> querySportCourt();
	SportCenterCourt querySportCourt(Long idCourt);
	void removeAll() throws Exception;
	List<SportCenterCourt> querySportCourts(Long idSportCenter);
	List<SportCenterCourt> querySportCourtsByTownAndSport(Long idTown, Long idSport);
	void updateSportCourt(SportsCourtForm sportsCourtForm) throws Exception;
}
