package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.SportVO;

public interface SportsManager {	
	public List<SportVO> querySports();
	public SportVO querySportsById(Long id);
	public SportVO querySportsByName(String sportName);
	public void add(SportVO sportVO) throws Exception;	
	public boolean remove(SportVO sportVO) throws Exception;	
	public boolean update(SportVO sportVO) throws Exception;
}
