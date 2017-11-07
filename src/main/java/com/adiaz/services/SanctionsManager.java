package com.adiaz.services;

import com.adiaz.entities.Category;
import com.adiaz.entities.Sanction;
import com.adiaz.forms.SanctionForm;

import java.util.List;

/**
 * Created by toni on 28/09/2017.
 */

public interface SanctionsManager {

	List<Sanction> querySanctionsByIdCompetition(Long idCompetition);

	void add(SanctionForm sanctionForm) throws Exception;

	boolean remove(Long id) throws Exception;
	void removeByCompetition (Long idCompetition) throws Exception;

}
