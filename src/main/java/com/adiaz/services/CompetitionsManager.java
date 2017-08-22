package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;
import com.adiaz.forms.CompetitionsFilterForm;
import com.adiaz.forms.CompetitionsForm;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CompetitionsManager {
	public Long add(Competition item) throws Exception;
	public Long add(CompetitionsForm competitionsForm) throws Exception;
	public boolean remove(Competition item) throws Exception;
	public boolean update(Competition competition) throws Exception;
	public boolean update(Long idCompetition, CompetitionsForm competitionsForm) throws Exception;
	public List<Competition> queryCompetitions();
	public List<Competition> queryCompetitions(Long idSport, Long idCategory, Long idTown);
	public List<Competition> queryCompetitionsByTown(long idTown, boolean onlyPublished);
	public List<Competition> queryCompetitions(CompetitionsFilterForm competitionsFilterForm);
	public CompetitionsForm queryCompetitionsById(long id);
	public Competition queryCompetitionsByIdEntity(long id);
	public List<Competition> queryCompetitionsBySport(Sport sport);
	public void removeAll() throws Exception;
}
