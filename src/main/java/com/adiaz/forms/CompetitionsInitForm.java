package com.adiaz.forms;

import com.adiaz.entities.Parameter;
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


    public int calculateWeeks(){
        // 10 teams --> 18 weeks
        // 9 teams --> 18 weeks, each week one team doesn't play.
        return (teamsCount + (teamsCount%2)) * 2 - 2;
    }

    public int calculateMatchesEachWeek() {
        return (teamsCount + teamsCount%2)/2;
    }

    public int calculateTotalMatchesCount() {
        return calculateWeeks() * calculateMatchesEachWeek();
    }

    public ParsedCalendar parseCalendar(){
        ParsedCalendar parsedCalendar = new ParsedCalendar();
        String regex = "Jornada [\\d]*\\r\\n(((.*\\t){4}.*(\\r\\n)*){"+ this.calculateMatchesEachWeek() +"})";
        Pattern patternAllWeek = Pattern.compile(regex);
        Pattern patternMatch = Pattern.compile("(.*)\\t(.*)\\t(.*)\\t(.*)\\t(.*)");
        Matcher matcher = patternAllWeek.matcher(this.getMatchesTxt());
        List<MatchForm> matchesList = new ArrayList<>();
        Set<String> teams = new HashSet<>();
        int weekCount = 0;
        while (matcher.find()) {
            Matcher matcherMatch = patternMatch.matcher(matcher.group(1));
            weekCount++;
            while (matcherMatch.find()) {
                teams.add(matcherMatch.group(3));
                teams.add(matcherMatch.group(4));
                MatchForm matchForm = new MatchForm();
                matchForm.setWeek(weekCount);
                matchForm.setDateStr(matcherMatch.group(1) + " " + matcherMatch.group(2));
                matchForm.setTeamLocalName(matcherMatch.group(3));
                matchForm.setTeamVisitorName(matcherMatch.group(4));
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
