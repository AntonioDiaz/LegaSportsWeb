package com.adiaz.services;

import com.adiaz.entities.Team;
import com.adiaz.forms.TeamFilterForm;
import com.adiaz.forms.TeamForm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by toni on 25/07/2017.
 */
public interface TeamManager {
	Long add(TeamForm teamForm) throws Exception;
	boolean remove(Long id) throws Exception;
	void removeAll() throws Exception;
	boolean update(TeamForm teamForm) throws Exception;
	TeamForm queryById(Long id);
	List<Team> queryByFilter(TeamFilterForm teamFilterForm);
	List<Team> queryByCompetition(Long idCompetition);
	List<Team> queryByTown(Long idTown);
}
