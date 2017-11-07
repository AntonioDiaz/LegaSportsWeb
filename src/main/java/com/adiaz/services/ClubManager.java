package com.adiaz.services;

import com.adiaz.entities.Club;
import com.adiaz.forms.ClubForm;

import java.util.List;

/**
 * Created by toni on 24/07/2017.
 */
public interface ClubManager {
	List<Club> queryAll();
	List<Club> queryByTownId(Long townId);
	ClubForm queryById(Long id);
	Long add(ClubForm clubForm) throws Exception;
	boolean update (ClubForm clubForm) throws Exception;
	boolean remove(Long id) throws Exception;
	void removaAll() throws Exception;
	boolean isElegibleForDelete(Long idClub);
}
