package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.*;
import com.adiaz.forms.CompetitionsFilterForm;
import com.adiaz.forms.CompetitionsForm;
import com.adiaz.forms.utils.CompetitionsFormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.MatchesDAO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("competitionsManager")
public class CompetitionsManagerImpl implements CompetitionsManager {

	@Autowired CompetitionsDAO competitionsDAO;
	@Autowired MatchesDAO matchesDAO;
	@Autowired CompetitionsFormUtils competitionsFormUtils;
	
	@Override
	public Long add(Competition competition) throws Exception {
		return competitionsDAO.create(competition).getId();
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
	public boolean update(CompetitionsForm competitionsForm) throws Exception {
		Competition competition = competitionsFormUtils.formToEntity(competitionsForm);
		return competitionsDAO.update(competition);
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
	@Override
	public CompetitionsForm queryCompetitionsById(long id) {
		Competition competition = competitionsDAO.findCompetitionsById(id);
		return competitionsFormUtils.entityToForm(competition);
	}

	@Override
	public Competition queryCompetitionsByIdEntity(long id) {
		return competitionsDAO.findCompetitionsById(id);
	}

	@Override
	public Long add(CompetitionsForm competitionsForm) throws Exception {
		Competition competition = competitionsFormUtils.formToEntity(competitionsForm);
		return add(competition);
	}

	@Override
	public List<Competition> queryCompetitions(Long idSport, Long idCategory, Long idTown) {
		return competitionsDAO.findCompetitions(idSport, idCategory, idTown);
	}

	@Override
	public List<Competition> queryCompetitions(CompetitionsFilterForm f) {
		return competitionsDAO.findCompetitions(f.getIdSport(), f.getIdCategory(), f.getIdTown());
	}

	@Override
	public void removeAll() throws Exception {
		List<Competition> competitions = competitionsDAO.findCompetitions();
		for (Competition competition : competitions) {
			competitionsDAO.remove(competition);
		}
	}
}
