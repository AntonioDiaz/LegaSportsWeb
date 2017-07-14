package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.MatchesDAO;
import com.adiaz.entities.Category;
import com.adiaz.entities.Match;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("competitionsManager")
public class CompetitionsManagerImpl implements CompetitionsManager {

	@Autowired CompetitionsDAO competitionsDAO;
	@Autowired MatchesDAO matchesDAO;
	
	@Override
	public void add(Competition item) throws Exception {
		competitionsDAO.create(item);
	}

	@Override
	public boolean remove(Competition competition) throws Exception {
		List<Match> queryMatchesByCompetition = matchesDAO.findByCompetition(competition.getId());
		for (Match match : queryMatchesByCompetition) {
			matchesDAO.remove(match);
		}
		return competitionsDAO.remove(competition);		
	}
	
	@Override
	public boolean update(Competition item) throws Exception {
		return competitionsDAO.update(item);
	}
	
	@Override
	public List<Competition> queryCompetitions() {
		return competitionsDAO.findCompetitions();
	}

	@Override
	public List<Competition> queryCompetitionsBySport(Sport sport) {
		return competitionsDAO.findCompetitionsBySport(sport);
	}

	// TODO: 14/07/2017 remove this?? 
	public Competition queryCompetitionsById(long id) {
		Competition competition = null; 
		List<Competition> competitions = competitionsDAO.findCompetitions();
		for (Competition c : competitions) {
			if (id==c.getId()) {
				competition = c;
			}
		}
		return competition;
	}

	@Override
	public void add(String name, Long sportId, Long categoryId) throws Exception {
		Competition competition = new Competition();
		competition.setName(name);
		Key<Sport> keySport = Key.create(Sport.class, sportId);
		competition.setSport(Ref.create(keySport));
		Key<Category> keyCategory = Key.create(Category.class, categoryId);
		competition.setCategory(Ref.create(keyCategory));
		competitionsDAO.create(competition);
	}

	@Override
	public List<Competition> queryCompetitions(Long idSport, Long idCategory) {
		return competitionsDAO.findCompetitions(idSport, idCategory);
	}

	@Override
	public void removeAll() throws Exception {
		List<Competition> competitions = competitionsDAO.findCompetitions();
		for (Competition competition : competitions) {
			competitionsDAO.remove(competition);
		}
	}
}
