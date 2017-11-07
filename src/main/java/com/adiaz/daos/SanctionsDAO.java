package com.adiaz.daos;

import com.adiaz.entities.Sanction;

import java.util.List;

/**
 * Created by toni on 28/09/2017.
 */
public interface SanctionsDAO extends GenericDAO<Sanction> {
	Sanction findById(Long id);
	List<Sanction> findByCompetitionId(Long id);
	List<Sanction> findByCompetitionIdAndTeamId(Long idCompetition, Long idTeam);
	void remove(Iterable<Sanction> sanctionsToRemove);
}
