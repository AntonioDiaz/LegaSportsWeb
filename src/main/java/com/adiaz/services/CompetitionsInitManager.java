package com.adiaz.services;

import com.adiaz.forms.CompetitionsInitForm;
import com.adiaz.utils.initcompetition.InitCompetitionResult;

public interface CompetitionsInitManager {
    InitCompetitionResult initCompetition(CompetitionsInitForm competitionsInitForm) throws Exception;
}
