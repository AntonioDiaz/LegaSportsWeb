package com.adiaz.utils.teamlist;


import com.adiaz.entities.Team;
import lombok.Data;

@Data
public class TeamListEntity {
    String teamName;
    String townName;
    String categoryName;
    String sportName;
    boolean elegibleForDelete;
    Long id;

    public TeamListEntity(Team team, boolean isElegibleForDelete) {
        teamName = team.getName();
        townName = team.getTownEntity().getName();
        categoryName = team.getCategoryEntity().getName();
        sportName = team.getSportEntity().getName();
        elegibleForDelete = isElegibleForDelete;
        id = team.getId();
    }
}
