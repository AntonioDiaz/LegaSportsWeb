package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Center;
import com.adiaz.forms.CenterForm;
import com.googlecode.objectify.Key;

public interface CenterManager {
	void addCenter(CenterForm centerForm) throws Exception;
	Key<Center> addCenter(Center center) throws Exception;
	boolean updateSportCenter(CenterForm centerForm) throws Exception;
	boolean removeSportCenter(Long id) throws Exception;
	List<Center> querySportCenters();
	List<Center> querySportCenters(Long idTown);
	CenterForm queryFormById(Long id);
	Center queryById(Long id);
	void removeAll() throws Exception;
	boolean isElegibleForDelete(Long idCenter);
}
