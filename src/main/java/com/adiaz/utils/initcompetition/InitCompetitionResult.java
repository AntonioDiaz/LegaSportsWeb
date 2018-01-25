package com.adiaz.utils.initcompetition;

import com.adiaz.entities.Competition;
import com.adiaz.utils.LocalSportsConstants;
import lombok.Data;

@Data
public class InitCompetitionResult {

    public static final String PARSE_ERROR_FORMAT = "Error al parsear.";
    public static final String PARSE_ERROR_WEEKS = "El numbero de jornadas no es correcto. Se esperaban %1$s, se han leido %2$s";
    public static final String PARSE_ERROR_TEAMS = "El equipos no es correcto. Se esperaban %1$s, se han leido %2$s";
    public static final String PARSE_ERROR_MATCHES = "Hay alguna jornada con un número incorrecto de partidos.";
    public static final String PARSE_ERROR_COURTS = "Hay una pista que no que no existe: %1$s";
    public static final String PARSE_ERROR_UNKNOWN = "Se ha producido un error desconocido.";

    String errorDesc;
    Competition competition;

    public InitCompetitionResult(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public InitCompetitionResult(Competition competition) {
        this.competition = competition;
    }
}
