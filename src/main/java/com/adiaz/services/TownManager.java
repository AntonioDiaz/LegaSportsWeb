package com.adiaz.services;

import com.adiaz.entities.Town;
import com.adiaz.forms.TownForm;

import java.util.List;

/**
 * Created by toni on 14/07/2017.
 */
public interface TownManager {
	Long add(TownForm townForm) throws Exception;
	boolean remove(Long id) throws Exception;
	boolean update(Long id, TownForm townForm) throws Exception;
	List<Town> queryAll();
	List<Town> queryActives();
	TownForm queryById(Long id);
	List<Town> queryByName(String name);
	void removeAll() throws Exception;
	boolean isElegibleForDelete(Long idTown);
}
