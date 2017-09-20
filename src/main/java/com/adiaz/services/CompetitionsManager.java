package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sport;
import com.adiaz.forms.CompetitionsFilterForm;
import com.adiaz.forms.CompetitionsForm;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CompetitionsManager {
	Long add(Competition item) throws Exception;
	Long add(CompetitionsForm competitionsForm) throws Exception;
	boolean remove(Competition item) throws Exception;
	boolean update(Competition competition) throws Exception;
	boolean update(Long idCompetition, CompetitionsForm competitionsForm) throws Exception;
	List<Competition> queryCompetitions();
	List<Competition> queryCompetitions(Long idSport, Long idCategory, Long idTown);
	List<Competition> queryCompetitionsByTown(long idTown, boolean onlyPublished);
	List<Competition> queryCompetitions(CompetitionsFilterForm competitionsFilterForm);
	CompetitionsForm queryCompetitionsById(long id);
	Competition queryCompetitionsByIdEntity(long id);
	List<Competition> queryCompetitionsBySport(Long idSport);
	void removeAll() throws Exception;
}
