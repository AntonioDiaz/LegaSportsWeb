package com.adiaz.services;

import com.adiaz.forms.CompetitionsInitForm;

public interface CompetitionsInitManager {

    boolean validaCompetitionInput(CompetitionsInitForm competitionsInitForm);
    Long initCompetition(CompetitionsInitForm competitionsInitForm) throws Exception;
}
