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
	public Long add(TeamForm teamForm) throws Exception;
	public boolean remove(Long id) throws Exception;
	public void removeAll() throws Exception;
	public boolean update(TeamForm teamForm) throws Exception;
	public TeamForm queryById(Long id);
	public List<Team> queryByFilter(TeamFilterForm teamFilterForm);
	public List<Team> queryByCompetition(Long idCompetition);

}
