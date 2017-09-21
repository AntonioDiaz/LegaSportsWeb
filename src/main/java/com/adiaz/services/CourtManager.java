package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Court;
import com.adiaz.forms.CourtForm;
import com.googlecode.objectify.Ref;

public interface CourtManager {
	Ref<Court> addSportCourt(Court court) throws Exception;
	Ref<Court> addSportCourt(CourtForm sportCourtForm) throws Exception;
	boolean updateSportCourt(Court court) throws Exception;
	boolean removeSportCourt(Long idCourt) throws Exception;
	List<Court> querySportCourt();
	Court querySportCourt(Long idCourt);
	void removeAll() throws Exception;
	List<Court> querySportCourts(Long idCenter);
	List<Court> querySportCourtsByTownAndSport(Long idTown, Long idSport);
	void updateSportCourt(CourtForm courtForm) throws Exception;
	boolean isElegibleForDelete(Long idCourt);
}
