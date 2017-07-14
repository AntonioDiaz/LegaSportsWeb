package com.adiaz.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.MatchesDAO;
import com.adiaz.entities.Match;


@Service ("matchesManager")
public class MatchesManagerImpl implements MatchesManager {

	@Autowired
	MatchesDAO matchesDAO;
	
	@Override
	public void add(Match item) throws Exception {
		matchesDAO.create(item);
	}

	@Override
	public boolean remove(Match item) throws Exception {
		return matchesDAO.remove(item);
	}

	@Override
	public boolean update(Match item) throws Exception {
		return matchesDAO.update(item);
	}

	@Override
	public List<Match> queryMatchesByCompetition(Long competitionId) {
		return matchesDAO.findByCompetition(competitionId);
	}

	@Override
	public void add(List<Match> matchesList) throws Exception {
		for (Match match : matchesList) {
			this.add(match);
		}
	}

	@Override
	public List<Match> queryMatches() {
		return matchesDAO.findAll();
	}

	@Override
	public Match queryMatchesById(Long id) {
		return matchesDAO.findById(id);
	}

	@Override
    public Integer howManyWeek(List<Match> matchesList) {
        Set<Integer> diferentsWeeks = new HashSet<Integer>();
        for (Match match : matchesList) {
            diferentsWeeks.add(match.getWeek());
        }
        return diferentsWeeks.size();
    }

    @Override
	public void removeAll() throws Exception {
		List<Match> queryAllMatches = matchesDAO.findAll();
		for (Match match : queryAllMatches) {
			matchesDAO.remove(match);
		}		
	}



}
