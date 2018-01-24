package com.adiaz.forms;

import com.adiaz.entities.Parameter;
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


    private int calculateWeeks(){
        // 10 teams --> 18 weeks
        // 9 teams --> 18 weeks, each week one team doesn't play.
        return (teamsCount + (teamsCount%2)) * 2 - 2;
    }

    private int calculateMatchesEachWeekInput() {
        return (teamsCount - teamsCount%2)/2;
    }

    private int calculateMatchesEachWeekOutput() {
        return (teamsCount + teamsCount%2)/2;
    }

    private int calculateTotalMatchesCount() {
        return calculateWeeks() * calculateMatchesEachWeekOutput();
    }

    public ParsedCalendar parseCalendar() throws Exception {
        ParsedCalendar parsedCalendar = new ParsedCalendar();
        this.setMatchesTxt(this.getMatchesTxt().replaceAll("\"", "'"));
        this.setMatchesTxt(this.getMatchesTxt().replaceAll("\r\n", "\n"));
        String regex = "Jornada [\\d]*\\n(((.*\\t){4}.*(\\n)*){"+ this.calculateMatchesEachWeekInput() +"})";
        Pattern patternAllWeek = Pattern.compile(regex);
        Pattern patternMatch = Pattern.compile("(?m)^.*$");
        Matcher matcher = patternAllWeek.matcher(this.getMatchesTxt());
        List<MatchForm> matchesList = new ArrayList<>();
        Set<String> teams = new HashSet<>();
        while (matcher.find()) {
            Matcher matcherMatch = patternMatch.matcher(matcher.group(1));
            while (matcherMatch.find()) {
                String[] split = matcherMatch.group().split("\\t");
                teams.add(split[2]);
                teams.add(split[3]);
            }
        }
        boolean oddNumberOfTeams = teams.size()%2==1;

        matcher = patternAllWeek.matcher(this.getMatchesTxt());
        int weekCount = 0;
        while (matcher.find()) {
            Matcher matcherMatch = patternMatch.matcher(matcher.group(1));
            weekCount++;
            Set<String> teamsSetCopy = new HashSet<>(teams);
            while (matcherMatch.find()) {
                String[] split = matcherMatch.group().split("\\t");
                MatchForm matchForm = new MatchForm();
                matchForm.setWeek(weekCount);
                matchForm.setDateStr(split[0] + " " + split[1]);
                String teamLocalName = split[2];
                String teamVisitorName = split[3];
                matchForm.setTeamLocalName(teamLocalName);
                matchForm.setTeamVisitorName(teamVisitorName);
                matchForm.setState(LocalSportsConstants.MATCH_STATE_PENDING);
                matchesList.add(matchForm);
                teamsSetCopy.remove(teamLocalName);
                teamsSetCopy.remove(teamVisitorName);
            }
            if (oddNumberOfTeams) {
                MatchForm matchForm = new MatchForm();
                matchForm.setWeek(weekCount);
                matchForm.setDateStr("");
                matchForm.setTeamLocalName(teamsSetCopy.iterator().next());
                matchForm.setTeamVisitorName("");
                matchForm.setState(LocalSportsConstants.MATCH_STATE_REST);
                matchesList.add(matchForm);
            }
        }
        //check if is valid
        boolean teamsValid = this.getTeamsCount() == teams.size();
        boolean weeksValid = this.calculateTotalMatchesCount() == matchesList.size();
        parsedCalendar.setValid(weeksValid && teamsValid);
        if (parsedCalendar.isValid()) {
            parsedCalendar.setMatchesParsed(matchesList);
            parsedCalendar.setTeamsParsed(new ArrayList<>(teams));
        }
        return parsedCalendar;
    }

    @Data
    public class ParsedCalendar {
        private List<String> teamsParsed;
        private List<MatchForm> matchesParsed;
        private boolean isValid;
    }
}
