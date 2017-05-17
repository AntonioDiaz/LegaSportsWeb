package com.adiaz.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adiaz.entities.CategoriesVO;
import com.adiaz.entities.ClassificationEntryVO;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.entities.SportVO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

public class UtilsLegaSport {

	
	//private static final Logger log = Logger.getLogger(UtilsLegaSport.class.getName());

	public static List<MatchesVO> parseCalendar(CompetitionsVO competition) {
		String calendarTxt = "";
		ClassLoader classLoader = UtilsLegaSport.class.getClassLoader();
		File file = new File(classLoader.getResource("static_calendar.txt").getFile());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null ){
				calendarTxt += line + "\r\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parseCalendar (calendarTxt, competition);
	}
	
	public static List<MatchesVO> parseCalendar(String matchesTxt, CompetitionsVO competition) {
		List<MatchesVO> matchesList = new ArrayList<>();
		String[] split = matchesTxt.split("\\r\\n");
		int week = 0;
		for (int i = 0; i < split.length; i++) {
			String line = split[i];
			if (line.startsWith("Jornada")) {
				week++;
			} else {
				String[] strings = line.split("\\t");
				MatchesVO matchesVO = new MatchesVO();
				matchesVO.setWeek(week);
				matchesVO.setTeamLocal(strings[2]);
				matchesVO.setTeamVisitor(strings[3]);
				if (strings.length>=5) {
					matchesVO.setPlace(strings[4]);
				}
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date myDate;
				try {
					myDate = dateFormat.parse(strings[0] + " " + strings[1]);
					matchesVO.setDate(myDate);
				} catch (ParseException e) {				
					e.printStackTrace();
				}
				Key<CompetitionsVO> competitionKey = Key.create(CompetitionsVO.class, competition.getId());
				matchesVO.setCompetitionRef(Ref.create(competitionKey));
				matchesList.add(matchesVO);
			}				
		}
		return matchesList;
	}
	
	public static List<ClassificationEntryVO> parseClassification(String classificationTxt, Long competitionId) {
		List<ClassificationEntryVO> classificationList = new ArrayList<ClassificationEntryVO>();
		String[] split = classificationTxt.split("\\r\\n");
		for (int i = 0; i < split.length; i++) {
			ClassificationEntryVO classificationEntryVO = new ClassificationEntryVO();
			String[] strings = split[i].split("\\t");			
			System.out.println(strings.length);
			//1	AD CEPA SPORT	22	18	1	3	85	24	0	0	0	55	0
			classificationEntryVO.setPosition(Integer.valueOf(strings[0]));
			classificationEntryVO.setTeam(strings[1]);
			classificationEntryVO.setPoints(Integer.valueOf(strings[11]));
			classificationEntryVO.setMatchesPlayed(Integer.valueOf(strings[2]));
			classificationEntryVO.setMatchesWon(Integer.valueOf(strings[3]));
			classificationEntryVO.setMatchesDrawn(Integer.valueOf(strings[4]));
			classificationEntryVO.setMatchesLost(Integer.valueOf(strings[5]));
			Key<CompetitionsVO> competitionKey = Key.create(CompetitionsVO.class, competitionId);
			classificationEntryVO.setCompetitionRef(Ref.create(competitionKey));			
			classificationList.add(classificationEntryVO);
		}
		return classificationList;
	}
	
	public static void main(String[] args) {
		ObjectifyService.register(SportVO.class);
		ObjectifyService.register(CategoriesVO.class);
		ObjectifyService.register(CompetitionsVO.class);
		ObjectifyService.register(MatchesVO.class);
		System.out.println("parse calendar....");
		String calendarTxt = "";
		ClassLoader classLoader = UtilsLegaSport.class.getClassLoader();
		File file = new File(classLoader.getResource("static_classification.txt").getFile());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null ){
				calendarTxt += line + "\r\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(UtilsLegaSport.parseClassification(calendarTxt, 1L).size());
	}
}
