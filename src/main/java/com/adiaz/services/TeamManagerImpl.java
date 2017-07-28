package com.adiaz.services;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.TeamDAO;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Team;
import com.adiaz.forms.TeamFilterForm;
import com.adiaz.forms.TeamForm;
import com.adiaz.forms.utils.TeamFormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by toni on 25/07/2017.
 */
@Component
public class TeamManagerImpl implements TeamManager {

	@Autowired
	TeamDAO teamDAO;
	@Autowired
	TeamFormUtils teamFormUtils;
	@Autowired
	CompetitionsDAO competitionsDAO;

	@Override
	public Long add(TeamForm teamForm) throws Exception {
		Team team = teamFormUtils.formToEntity(teamForm);
		return teamDAO.create(team).getId();
	}

	@Override
	public boolean remove(Long id) throws Exception {
		Team team = new Team();
		team.setId(id);
		return teamDAO.remove(team);
	}

	@Override
	public void removeAll() throws Exception {
		for (Team team : teamDAO.findAll()) {
			teamDAO.remove(team);
		}
	}

	@Override
	public boolean update(TeamForm teamForm) throws Exception {
		Team team = teamFormUtils.formToEntity(teamForm);
		return teamDAO.update(team);
	}

	@Override
	public TeamForm queryById(Long id) {
		TeamForm teamForm = null;
		Team team = teamDAO.findById(id);
		if (team!=null) {
			teamForm = teamFormUtils.entityToForm(team);
		}
		return teamForm;
	}

	@Override
	public List<Team> queryByFilter(TeamFilterForm filterForm) {
		return teamDAO.find(filterForm.getIdTown(), filterForm.getIdCategory(), filterForm.getIdSport());
	}

	@Override
	public List<Team> queryByCompetition(Long idCompetition) {
		Competition competition = competitionsDAO.findCompetitionsById(idCompetition);
		Long idTown = competition.getTownEntity().getId();
		Long idCategory = competition.getCategoryEntity().getId();
		Long idSport = competition.getSportEntity().getId();
		return teamDAO.find(idTown, idCategory, idSport);
	}
}
