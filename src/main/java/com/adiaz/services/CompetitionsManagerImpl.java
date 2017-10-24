package com.adiaz.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.adiaz.daos.ClassificationEntriesDAO;
import com.adiaz.entities.*;
import com.adiaz.forms.CompetitionsFilterForm;
import com.adiaz.forms.CompetitionsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.MatchesDAO;

@Service ("competitionsManager")
public class CompetitionsManagerImpl implements CompetitionsManager {

	@Autowired CompetitionsDAO competitionsDAO;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	@Autowired SanctionsManager sanctionsManager;

	@Override
	public Long add(Competition competition) throws Exception {
		return competitionsDAO.create(competition).getId();
	}

	@Override
	public Long add(CompetitionsForm competitionsForm) throws Exception {
		Competition competition = competitionsForm.formToEntity();
		return add(competition);
	}

	@Override
	public boolean remove(Competition competition) throws Exception {
		matchesManager.removeByCompetition(competition.getId());
		classificationManager.removeByCompetition(competition.getId());
		sanctionsManager.removeByCompetition(competition.getId());
		return competitionsDAO.remove(competition);
	}

	@Override
	public boolean update(Competition competition) throws Exception {
		return competitionsDAO.update(competition);
	}

	@Override
	public boolean update(Long idCompetition, CompetitionsForm competitionsForm) throws Exception {
		Competition competition = queryCompetitionsByIdEntity(idCompetition);
		competition = competitionsForm.formToEntity(competition);
		return competitionsDAO.update(competition);
	}
	
	@Override
	public List<Competition> queryCompetitions() {
		return competitionsDAO.findAll();
	}

	@Override
	public List<Competition> queryCompetitionsBySport(Long idSport) {
		return competitionsDAO.findBySport(idSport);
	}

	// TODO: 14/07/2017 remove this??
	@Override
	public CompetitionsForm queryCompetitionsById(long id) {
		Competition competition = competitionsDAO.findById(id);
		return new CompetitionsForm(competition);
	}

	@Override
	public Competition queryCompetitionsByIdEntity(long id) {
		return competitionsDAO.findById(id);
	}

	@Override
	public List<Competition> queryCompetitionsByTown(long idTown, boolean onlyPublished) {
		List<Competition> competitions = competitionsDAO.find(idTown, onlyPublished);
		Collections.sort(competitions, new Comparator<Competition>() {
			@Override
			public int compare(Competition o1, Competition o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return competitions;
	}

	@Override
	public List<Competition> queryCompetitions(Long idSport, Long idCategory, Long idTown) {
		List<Competition> competitions = competitionsDAO.find(idSport, idCategory, idTown);
		Collections.sort(competitions, new Comparator<Competition>() {
			@Override
			public int compare(Competition o1, Competition o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return competitions;
	}


	@Override
	public List<Competition> queryCompetitions(CompetitionsFilterForm f) {
		return queryCompetitions(f.getIdSport(), f.getIdCategory(), f.getIdTown());
	}

	@Override
	public void removeAll() throws Exception {
		List<Competition> competitions = competitionsDAO.findAll();
		for (Competition competition : competitions) {
			competitionsDAO.remove(competition);
		}
	}
}
