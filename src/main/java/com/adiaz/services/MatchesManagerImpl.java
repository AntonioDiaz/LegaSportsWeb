package com.adiaz.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adiaz.utils.RegisterEntities;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.MatchesDAO;
import com.adiaz.entities.Match;


@Service ("matchesManager")
public class MatchesManagerImpl implements MatchesManager {

	private static final Logger logger = Logger.getLogger(MatchesManagerImpl.class.getName());

	@Autowired
	MatchesDAO matchesDAO;
	
	@Override
	public Long add(Match match) throws Exception {
		return matchesDAO.create(match).getId();
	}

	@Override
	public Long addPublishedAndWorkingcopy(Match match) throws Exception {
		match.setWorkingCopy(false);
		Long idPublisedCopy = this.add(match);
		Key<Match> matchKey = Key.create(Match.class, idPublisedCopy);
		match.setId(null);
		match.setWorkingCopy(true);
		match.setMatchPublishedRef(Ref.create(matchKey));
		Long idWorkingCopy = this.add(match);
		logger.debug("idPublisedCopy " +  idPublisedCopy);
		logger.debug("idWorkingCopy " +  idWorkingCopy);
		return idWorkingCopy;
	}

	@Override
	public boolean remove(Match match) throws Exception {
		return matchesDAO.remove(match);
	}

	@Override
	public boolean update(Match match) throws Exception {
		return matchesDAO.update(match);
	}

	@Override
	public void updatePublishedMatches(Long idCompetition) throws Exception {
		List<Match> matches = queryMatchesByCompetitionWorkingCopy(idCompetition);
		for (Match match : matches) {
			// TODO: 22/07/2017 optimize this method, check if it is necessary to update.
			Match published = match.getMatchPublished();
			published.setScoreLocal(match.getScoreLocal());
			published.setScoreVisitor(match.getScoreVisitor());
			published.setDate(match.getDate());
			published.setSportCenterCourtRef(match.getSportCenterCourtRef());
			//// TODO: 22/07/2017 optimize this method, could be better to send a list of entities to update 
			update(published);
		}

	}

	@Override
	public List<Match> queryMatchesByCompetitionWorkingCopy(Long competitionId) {
		return matchesDAO.findByCompetition(competitionId, true);
	}

	@Override
	public List<Match> queryMatchesByCompetitionPublished(Long competitionId) {
		return matchesDAO.findByCompetition(competitionId, false);
	}

	@Override
	public void addMatchListAndPublish(List<Match> matchesList) throws Exception {
		for (Match match : matchesList) {
			addPublishedAndWorkingcopy(match);
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
        Set<Integer> diferentsWeeks = new HashSet<>();
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
