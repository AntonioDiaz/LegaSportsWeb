package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.MatchesVO;

public interface MatchesManager {

	public void add(MatchesVO item) throws Exception;	
	public boolean remove(MatchesVO item) throws Exception;	
	public void removeAll() throws Exception;	
	public boolean update(MatchesVO item) throws Exception;
	public List<MatchesVO> queryMatchesByCompetition(Long competitionId);
	public void add(List<MatchesVO> matchesList) throws Exception;
	public List<MatchesVO> queryMatches();
	public Integer howManyWeek(List<MatchesVO> matchesList);
}
