package com.adiaz.services;

import com.adiaz.entities.Club;
import com.adiaz.forms.ClubForm;

import java.util.List;

/**
 * Created by toni on 24/07/2017.
 */
public interface ClubManager {
	public List<Club> queryAll();
	public List<Club> queryByTownId(Long townId);
	public ClubForm queryById(Long id);
	public Long add(ClubForm clubForm) throws Exception;
	public boolean update (ClubForm clubForm) throws Exception;
	public boolean remove(Long id) throws Exception;
	public void removaAll() throws Exception;
}
