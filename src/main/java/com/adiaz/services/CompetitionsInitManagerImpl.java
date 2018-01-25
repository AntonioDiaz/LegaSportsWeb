package com.adiaz.services;

import com.adiaz.controllers.AdminController;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Court;
import com.adiaz.entities.Match;
import com.adiaz.entities.Team;
import com.adiaz.forms.*;
import com.adiaz.utils.LocalSportsConstants;
import com.adiaz.utils.LocalSportsUtils;
import com.adiaz.utils.initcompetition.InitCompetitionResult;
import com.adiaz.utils.initcompetition.MatchInput;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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

    @Autowired
    CourtManager courtManager;

    @Override
    public InitCompetitionResult initCompetition(CompetitionsInitForm form) throws Exception {
        List<List<MatchInput>> matchesList = new ArrayList<>();
        int matchesEachWeek = calculateMatchesEachWeek(form.getTeamsCount());
        int weeksNumber = calculateWeeksNumber(form.getTeamsCount());
        List<List<MatchInput>> weeks = null;
        try {
            String inputClean = formatInput(form.getMatchesTxt());
            weeks = parseCalendar(inputClean, matchesEachWeek);
        } catch (Exception e) {
            return new InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR.PARSE_ERROR_FORMAT);
        }
        if (weeks.size()==0) {
            return new InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR.PARSE_ERROR_FORMAT);
        }
        if (weeks.size()!=weeksNumber) {
            return new InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR.PARSE_ERROR_WEEKS);
        }
        List<String> teamsNames = filterTeams(weeks);
        if (teamsNames.size()!=form.getTeamsCount()) {
            return new InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR.PARSE_ERROR_TEAMS);
        }
        if (!validateMatchesEachWeek(weeks, matchesEachWeek)) {
            return new InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR.PARSE_ERROR_MATCHES);
        }
        if (!validateCourts(weeks, form.getIdTown(), form.getIdSport())) {
            return new InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR.PARSE_ERROR_COURTS);
        }
        Competition newCompetition;
        try {
            newCompetition = createCompetition(weeks, form);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new InitCompetitionResult(LocalSportsConstants.INIT_COMPETITION_ERROR.PARSE_ERROR_UNKNOWN);
        }
        return new InitCompetitionResult(newCompetition);
    }

    private Competition createCompetition(List<List<MatchInput>> weeks, CompetitionsInitForm form) throws Exception {
        List<String> teamsNames = filterTeams(weeks);
        Long[] teamsIds = createTeams(teamsNames, form.getIdTown(), form.getIdCategory(), form.getIdSport());
        Long idCompetition = createCompetitionEntity(form.getName(), form.getIdTown(), form.getIdCategory(), form.getIdSport(), teamsIds);
        Competition competition = competitionsManager.queryCompetitionsByIdEntity(idCompetition);
        createMatchesInCompetition(weeks, competition);
        return competitionsManager.queryCompetitionsByIdEntity(idCompetition);
    }

    private void createMatchesInCompetition(List<List<MatchInput>> weeks, Competition competition) throws Exception {
        Ref<Competition> competitionRef = Ref.create(competition);
        Map<String, Ref<Team>> teamsMap = new HashMap<>();
        for (Ref<Team> teamRef : competition.getTeams()) {
            teamsMap.put(teamRef.get().getName(), teamRef);
        }
        Long idTown = competition.getTownRef().get().getId();
        Long idSport = competition.getSportRef().get().getId();
        Map<String, Court> courtsMap = courtManager.querySportsCourtsByTownAndSportsMap(idTown, idSport);
        List<Match> matchesList = new ArrayList<>();
        Set<String>teamsNames = new HashSet<>(filterTeams(weeks));
        for (int i = 0; i < weeks.size(); i++) {
            List<MatchInput> matchInputs = weeks.get(i);
            Set<String>teamsNamesCopy = new HashSet<>(teamsNames);
            for (MatchInput matchInput : matchInputs) {
                Ref<Team> teamRefLocal = teamsMap.get(matchInput.getTeamLocalStr());
                Ref<Team> teamRefVisitor = teamsMap.get(matchInput.getTeamVisitorStr());
                Match match = new Match();
                match.setState(LocalSportsConstants.MATCH_STATE_PENDING);
                match.setTeamLocalRef(teamRefLocal);
                match.setTeamVisitorRef(teamRefVisitor);
                match.setDate(LocalSportsUtils.parseStringToDate(matchInput.getDateStr()));
                if (StringUtils.isNotBlank(matchInput.getCourtFullNameStr())) {
                    Ref<Court> courtRef = Ref.create(courtsMap.get(matchInput.getCourtFullNameStr()));
                    match.setCourtRef(courtRef);
                }
                match.setCompetitionRef(competitionRef);
                match.setWeek(i+1);
                matchesList.add(match);
                teamsNamesCopy.remove(matchInput.getTeamLocalStr());
                teamsNamesCopy.remove(matchInput.getTeamVisitorStr());
            }
            //now adding team who rest this week.
            if (teamsNamesCopy.size()>0) {
                Match match = new Match();
                match.setState(LocalSportsConstants.MATCH_STATE_REST);
                Ref<Team> teamRefLocal = teamsMap.get(teamsNamesCopy.iterator().next());
                match.setTeamLocalRef(teamRefLocal);
                match.setCompetitionRef(competitionRef);
                match.setWeek(i+1);
                matchesList.add(match);
            }
        }
        matchesManager.addMatchListAndPublish(matchesList);
    }

    private boolean validateCourts(List<List<MatchInput>> weeks, Long idTown, Long idSport) {
        Map<String, Court> courtsMap = courtManager.querySportsCourtsByTownAndSportsMap(idTown, idSport);
        for (List<MatchInput> week : weeks) {
            for (MatchInput matchInput : week) {
                String courtInput = matchInput.getCourtFullNameStr();
                if (StringUtils.isNotBlank(courtInput) && !courtsMap.containsKey(courtInput)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateMatchesEachWeek(List<List<MatchInput>> weeks, int matchesEachWeek) {
        for (List<MatchInput> week : weeks) {
            if (week.size()!=matchesEachWeek) {
                return false;
            }
        }
        return true;
    }

    private int calculateMatchesEachWeek(Integer teamsCount) {
        return (teamsCount - teamsCount%2)/2;
    }

    private int calculateWeeksNumber(Integer teamsCount) {
        // 10 teams --> 18 weeks
        // 9 teams --> 18 weeks, each week one team doesn't play.
        return (teamsCount + (teamsCount%2)) * 2 - 2;
    }

    private List<String> filterTeams(List<List<MatchInput>> weeks) {
        Set<String> teams = new HashSet<>();
        for (List<MatchInput> week : weeks) {
            for (MatchInput matchInput : week) {
                teams.add(matchInput.getTeamLocalStr());
                teams.add(matchInput.getTeamVisitorStr());
            }
        }
        return new ArrayList<>(teams);
    }

    private List<List<MatchInput>> parseCalendar(String input, int matchesEachWeek) throws IOException {
        List<List<MatchInput>> weeksParsed = new ArrayList<>();
        String regex = "Jornada.*[\\r?\\n](((.*\\t?){5}[\\r?\\n]?){"+ matchesEachWeek +"})";
        Pattern patternInput = Pattern.compile(regex);
        Pattern patternMatch = Pattern.compile("(?m)^.*$");
        Matcher matcher = patternInput.matcher(input);
        while (matcher.find()) {
            Matcher matcherMatch = patternMatch.matcher(matcher.group(1));
            List<MatchInput> matchInputList = new ArrayList<>();
            while (matcherMatch.find()) {
                String[] split = matcherMatch.group().split("\\t");
                MatchInput matchInput = new MatchInput();
                matchInput.setDateStr(split[0] + " " + split[1]);
                matchInput.setTeamLocalStr(split[2]);
                matchInput.setTeamVisitorStr(split[3]);
                matchInput.setCourtFullNameStr(split[4]);
                matchInputList.add(matchInput);
            }
            weeksParsed.add(matchInputList);
        }
        return weeksParsed;
    }

    private String formatInput(String f) throws IOException {
        //remove lines with descansa and double quotes.
        f = f.replaceAll("\"", "'");
        BufferedReader bufReader = new BufferedReader(new StringReader(f));
        StringBuilder stb = new StringBuilder();
        String line=null;
        while( (line=bufReader.readLine()) != null ) {
            if (!line.toLowerCase().contains(LocalSportsConstants.DESCANSA.toLowerCase())) {
                stb.append(line + "\n");
            }
        }
        return stb.toString();
    }

    private Long createCompetitionEntity(String name, Long idTown, Long idCategory, Long idSport, Long[] teams) throws Exception {
        CompetitionsForm competitionsForm = new CompetitionsForm();
        competitionsForm.setName(name);
        competitionsForm.setIdSport(idSport);
        competitionsForm.setIdCategory(idCategory);
        competitionsForm.setIdTown(idTown);
        competitionsForm.setTeams(teams);
        Long idCompetition = competitionsManager.add(competitionsForm);
        return idCompetition;
    }

    private Long[] createTeams(List<String> teamsList, Long idTown, long idCategory, long idSport) throws Exception {
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
        Long teamsIds[] = new Long[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            teamsIds[i] = teams.get(i).getId();
        }
        return teamsIds;
    }
}