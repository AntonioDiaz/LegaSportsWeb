package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.SportVO;

public interface CompetitionsManager {
	public void add(CompetitionsVO item) throws Exception;
	public void add(String competitionName, Long sportId, Long categoryId) throws Exception;
	public boolean remove(CompetitionsVO item) throws Exception;	
	public boolean update(CompetitionsVO item) throws Exception;
	public List<CompetitionsVO> queryCompetitions();
	public List<CompetitionsVO> queryCompetitions(Long idSport, Long idCategory);
	public CompetitionsVO queryCompetitionsById(long id);
	public List<CompetitionsVO> queryCompetitionsBySport(SportVO sportVO);
	public void removeAll() throws Exception;
}
