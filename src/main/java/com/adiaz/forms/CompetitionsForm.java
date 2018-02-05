package com.adiaz.forms;

import com.adiaz.entities.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompetitionsForm implements GenericForm<Competition> {

    private Long id;
    private String name;
    private Long idSport;
    private Long idCategory;
    private Long idTown;
    private Long[] teams;
    private boolean visible;
    private List<String> weeksNames;
    private Integer punctuationType;

    public CompetitionsForm() {
    }

    public CompetitionsForm(Competition competition) {
        id = competition.getId();
        name = competition.getName();
        visible = competition.isVisible();
        punctuationType = competition.getPuntuactionType();
        if (competition.getCategoryEntity() != null) {
            idCategory = competition.getCategoryEntity().getId();
        }
        if (competition.getSportEntity() != null) {
            idSport = competition.getSportEntity().getId();
        }
        if (competition.getTownEntity() != null) {
            idTown = competition.getTownEntity().getId();
        }
        List<Team> teamsList = competition.getTeamsDeref();
        if (competition.getTeams() != null) {
            teams = new Long[teamsList.size()];
            for (int i = 0; i < teamsList.size(); i++) {
                teams[i] = teamsList.get(i).getId();
            }
        }
        if (competition.getWeeksNames() != null) {
            weeksNames = competition.getWeeksNames();
        }
    }

    @Override
    public Competition formToEntity() {
        return formToEntity(new Competition());
    }

    @Override
    public Competition formToEntity(Competition competition) {
        competition.setId(id);
        competition.setName(name);
        competition.setVisible(visible);
        competition.setPuntuactionType(punctuationType);
        if (idCategory != null) {
            Ref<Category> categoryRef = Ref.create(Key.create(Category.class, idCategory));
            competition.setCategoryRef(categoryRef);
        }
        if (idSport != null) {
            Ref<Sport> sportRef = Ref.create(Key.create(Sport.class, idSport));
            competition.setSportRef(sportRef);
        }
        if (idTown != null) {
            Ref<Town> townRef = Ref.create(Key.create(Town.class, idTown));
            competition.setTownRef(townRef);
        }
        List<Ref<Team>> teamsRefList = new ArrayList<>();
        for (int i = 0; i < teams.length; i++) {
            Ref<Team> teamRef = Ref.create(Key.create(Team.class, teams[i]));
            teamsRefList.add(teamRef);
        }
        competition.setTeams(teamsRefList);
        if (weeksNames != null) {
            competition.setWeeksNames(weeksNames);
        }
        return competition;
    }
}
