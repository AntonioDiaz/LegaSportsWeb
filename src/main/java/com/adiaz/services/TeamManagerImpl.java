package com.adiaz.services;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.TeamDAO;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Team;
import com.adiaz.forms.TeamFilterForm;
import com.adiaz.forms.TeamForm;
import com.googlecode.objectify.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by toni on 25/07/2017.
 */
@Component
public class TeamManagerImpl implements TeamManager {

	@Autowired
	TeamDAO teamDAO;
	@Autowired
	CompetitionsDAO competitionsDAO;

	@Override
	public Long add(TeamForm teamForm) throws Exception {
		Team team = teamForm.formToEntity();
		return teamDAO.create(team).getId();
	}

	@Override
	public List<Key<Team>> add(List<Team> teamList) throws Exception {
		Map<Key<Team>, Team> keyTeamMap = teamDAO.create(teamList);
		return new ArrayList<>(keyTeamMap.keySet());

	}

	@Override
	public boolean remove(Long id) throws Exception {
		boolean deleteDone = false;
		if (isElegibleForDelete(id)) {
			Team team = new Team();
			team.setId(id);
			deleteDone = teamDAO.remove(team);
		}
		return deleteDone;
	}

	// TODO: 20/09/2017 delete this
	@Override
	public void removeAll() throws Exception {
		for (Team team : teamDAO.findAll()) {
			teamDAO.remove(team);
		}
	}

	@Override
	public boolean update(TeamForm teamForm) throws Exception {
		Team team = teamForm.formToEntity();
		return teamDAO.update(team);
	}

	@Override
	public TeamForm queryById(Long id) {
		TeamForm teamForm = null;
		Team team = teamDAO.findById(id);
		if (team!=null) {
			teamForm = new TeamForm(team);
		}
		return teamForm;
	}

	@Override
	public List<Team> queryByFilter(TeamFilterForm filterForm) {
		return teamDAO.find(filterForm.getIdTown(), filterForm.getIdCategory(), filterForm.getIdSport());
	}

	@Override
	public List<Team> queryByCompetition(Long idCompetition) {
		Competition competition = competitionsDAO.findById(idCompetition);
		Long idTown = competition.getTownEntity().getId();
		Long idCategory = competition.getCategoryEntity().getId();
		Long idSport = competition.getSportEntity().getId();
		return teamDAO.find(idTown, idCategory, idSport);
	}

	@Override
	public List<Team> queryByTown(Long idTown) {
		return teamDAO.findBySport(idTown);
	}

	@Override
	public boolean isElegibleForDelete(Long idTeam) {
		return competitionsDAO.findByTeam(idTeam).isEmpty();
	}
}
