package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;

public interface CompetitionsManager {
	public void add(Competition item) throws Exception;
	public void add(String competitionName, Long sportId, Long categoryId) throws Exception;
	public boolean remove(Competition item) throws Exception;
	public boolean update(Competition item) throws Exception;
	public List<Competition> queryCompetitions();
	public List<Competition> queryCompetitions(Long idSport, Long idCategory);
	public Competition queryCompetitionsById(long id);
	public List<Competition> queryCompetitionsBySport(Sport sport);
	public void removeAll() throws Exception;
}
