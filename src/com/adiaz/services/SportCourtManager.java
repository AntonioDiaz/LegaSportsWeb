package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportCourt;
import com.googlecode.objectify.Ref;

public interface SportCourtManager {

	public Ref<SportCourt> addSportCourt(SportCourt sportCourt) throws Exception;
	public boolean updateSportCourt(SportCourt sportCourt) throws Exception;
	public boolean removeSportCourt(SportCourt sportCourt) throws Exception;
	public List<SportCourt> querySportCourt();
	public void removeAll() throws Exception;

}
