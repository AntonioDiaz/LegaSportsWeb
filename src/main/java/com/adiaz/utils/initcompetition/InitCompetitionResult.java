package com.adiaz.utils.initcompetition;

import com.adiaz.entities.Competition;
import com.adiaz.utils.LocalSportsConstants;
import lombok.Data;

@Data
public class InitCompetitionResult {
    LocalSportsConstants.INIT_COMPETITION_ERROR error;
    Competition competition;

    public InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR error) {
        this.error = error;
    }

    public InitCompetitionResult(Competition competition) {
        this.competition = competition;
    }
}
