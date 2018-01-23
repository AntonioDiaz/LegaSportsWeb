package com.adiaz.services;

import com.adiaz.controllers.AdminController;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Court;
import com.adiaz.entities.Match;
import com.adiaz.entities.Team;
import com.adiaz.forms.*;
import com.adiaz.utils.LocalSportsConstants;
import com.adiaz.utils.LocalSportsUtils;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("competitionsInitManager")
public class CompetitionsInitManagerImpl implements CompetitionsInitManager {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    TeamManager teamManager;

    @Autowired
    CompetitionsManager competitionsManager;

    @Autowired
    MatchesManager matchesManager;

    @Autowired
    ClassificationManager classificationManager;

    @Override
    public boolean validaCompetitionInput(CompetitionsInitForm competitionsInitForm) {
        CompetitionsInitForm.ParsedCalendar parsedCalendar = competitionsInitForm.parseCalendar();
        return parsedCalendar.isValid();
    }

    @Override
    public Long initCompetition(CompetitionsInitForm f) throws Exception {
        CompetitionsInitForm.ParsedCalendar parsedCalendar = f.parseCalendar();
        List<Key<Team>> teams = this.createTeams(parsedCalendar.getTeamsParsed(), f.getIdTown(), f.getIdCategory(), f.getIdSport());
        Long teamsIds[] = new Long[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            teamsIds[i] = teams.get(i).getId();
        }
        Long idCompetition = createCompetitionEntity(f.getName(), f.getIdTown(), f.getIdCategory(), f.getIdSport(), teamsIds);
        Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
        Key<Court> key = Key.create(Court.class, f.getIdCourt());
        Ref<Court> courtRef = Ref.create(key);
        createMatches(parsedCalendar.getMatchesParsed(), competition, courtRef);
        return idCompetition;
    }

    private void createMatches(List<MatchForm> matchesFormList, Competition competition, Ref<Court> refCourt) throws Exception {
        Ref<Competition> competitionRef = Ref.create(competition);
        Map<String, Ref<Team>> teamsMap = new HashMap<>();
        for (Ref<Team> teamRef : competition.getTeams()) {
            teamsMap.put(teamRef.get().getName(), teamRef);
        }
        List<Match> matchesList = new ArrayList<>();
        for (MatchForm matchForm : matchesFormList) {
            Ref<Team> teamRefLocal = teamsMap.get(matchForm.getTeamLocalName());
            Ref<Team> teamRefVisitor = teamsMap.get(matchForm.getTeamVisitorName());
            Match match = new Match();
            match.setState(LocalSportsConstants.MATCH_STATE_PENDING);
            match.setTeamLocalRef(teamRefLocal);
            match.setTeamVisitorRef(teamRefVisitor);
            match.setDate(LocalSportsUtils.parseStringToDate(matchForm.getDateStr()));
            match.setCourtRef(refCourt);
            match.setCompetitionRef(competitionRef);
            match.setWeek(matchForm.getWeek());
            matchesList.add(match);
        }
        matchesManager.addMatchListAndPublish(matchesList);
    }

    private Long createCompetitionEntity(String name, Long townIdLega, Long idCategory, Long idSport, Long[] teams) throws Exception {
        CompetitionsForm competitionsForm = new CompetitionsForm();
        competitionsForm.setName(name);
        competitionsForm.setIdSport(idSport);
        competitionsForm.setIdCategory(idCategory);
        competitionsForm.setIdTown(townIdLega);
        competitionsForm.setTeams(teams);
        Long idCompetition = competitionsManager.add(competitionsForm);
        return idCompetition;
    }

    private List<Key<Team>> createTeams(List<String> teamsList, Long idTown, long idCategory, long idSport) throws Exception {
        List<Team> teams = new LinkedList<>();
        for (String teamName : teamsList) {
            TeamForm teamForm = new TeamForm();
            teamForm.setName(teamName);
            teamForm.setIdTown(idTown);
            teamForm.setIdCategory(idCategory);
            teamForm.setIdSport(idSport);
            teams.add(teamForm.formToEntity());
        }
        List<Key<Team>> keyList = teamManager.add(teams);
        return keyList;
    }
}
