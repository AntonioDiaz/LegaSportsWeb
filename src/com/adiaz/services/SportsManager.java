package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportVO;
import com.googlecode.objectify.Key;

public interface SportsManager {	
	public List<SportVO> querySports();
	public SportVO querySportsById(Long id);
	public SportVO querySportsByName(String sportName);
	public Key<SportVO> add(SportVO sportVO) throws Exception;	
	public boolean remove(SportVO sportVO) throws Exception;	
	public boolean update(SportVO sportVO) throws Exception;
	public void removeAll() throws Exception;
}
