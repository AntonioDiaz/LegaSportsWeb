package com.adiaz.services;

import com.adiaz.entities.Town;
import com.adiaz.forms.TownForm;

import java.util.List;

/**
 * Created by toni on 14/07/2017.
 */
public interface TownManager {

	public void add(TownForm townForm) throws Exception;
	public boolean remove(Long id) throws Exception;
	public boolean update(Long id, TownForm townForm) throws Exception;
	public List<Town> queryAll();
	public TownForm queryById(Long id);
	public void removeAll() throws Exception;
}
