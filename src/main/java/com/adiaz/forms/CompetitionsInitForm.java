package com.adiaz.forms;

import com.adiaz.entities.Court;
import com.adiaz.utils.LocalSportsConstants;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class CompetitionsInitForm {

    private String name;
    private Long idSport;
    private Long idCategory;
    private Long idTown;
    private Long idCourt;
    private Integer teamsCount;
    private String matchesTxt;
    private Integer inputFormat;

}
