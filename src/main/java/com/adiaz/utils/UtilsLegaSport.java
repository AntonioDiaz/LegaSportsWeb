package com.adiaz.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adiaz.entities.*;
import com.adiaz.entities.Competition;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

public class UtilsLegaSport {

	
	//private static final Logger log = Logger.getLogger(UtilsLegaSport.class.getName());

	public static List<Match> parseCalendar(Competition competition) {
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
	
	public static List<Match> parseCalendar(String matchesTxt, Competition competition) {
		List<Match> matchesList = new ArrayList<>();
		String[] split = matchesTxt.split("\\r\\n");
		int week = 0;
		for (int i = 0; i < split.length; i++) {
			String line = split[i];
			if (line.startsWith("Jornada")) {
				week++;
			} else {
				String[] strings = line.split("\\t");
				Match match = new Match();
				match.setWeek(week);
				match.setTeamLocal(strings[2]);
				match.setTeamVisitor(strings[3]);
				if (strings.length>=5) {
					match.setPlace(strings[4]);
				}
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date myDate;
				try {
					myDate = dateFormat.parse(strings[0] + " " + strings[1]);
					match.setDate(myDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Key<Competition> competitionKey = Key.create(Competition.class, competition.getId());
				match.setCompetitionRef(Ref.create(competitionKey));
				matchesList.add(match);
			}				
		}
		return matchesList;
	}
	
	public static List<ClassificationEntry> parseClassification(String classificationTxt, Long competitionId) {
		List<ClassificationEntry> classificationList = new ArrayList<ClassificationEntry>();
		String[] split = classificationTxt.split("\\r\\n");
		for (int i = 0; i < split.length; i++) {
			ClassificationEntry classificationEntry = new ClassificationEntry();
			String[] strings = split[i].split("\\t");
			//1	AD CEPA SPORT	22	18	1	3	85	24	0	0	0	55	0
			classificationEntry.setPosition(Integer.valueOf(strings[0]));
			classificationEntry.setTeam(strings[1]);
			classificationEntry.setPoints(Integer.valueOf(strings[11]));
			classificationEntry.setMatchesPlayed(Integer.valueOf(strings[2]));
			classificationEntry.setMatchesWon(Integer.valueOf(strings[3]));
			classificationEntry.setMatchesDrawn(Integer.valueOf(strings[4]));
			classificationEntry.setMatchesLost(Integer.valueOf(strings[5]));
			Key<Competition> competitionKey = Key.create(Competition.class, competitionId);
			classificationEntry.setCompetitionRef(Ref.create(competitionKey));
			classificationList.add(classificationEntry);
		}
		return classificationList;
	}
	
	public static void main(String[] args) {
		ObjectifyService.register(Sport.class);
		ObjectifyService.register(Category.class);
		ObjectifyService.register(Competition.class);
		ObjectifyService.register(Match.class);
		System.out.println("parse calendar....");
		String calendarTxt = classificationStr();
		System.out.println(UtilsLegaSport.parseClassification(calendarTxt, 1L).size());
	}
	
	public static String classificationStr (){
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
		return calendarTxt;
	}
	
	
	public static String sha256Encode(String text) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
		byte[] digest = md.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			hexString.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		}
		return hexString.toString();
	}

	/**
	 * Validate the format of a field and if is null.
	 *
	 * @param errors
	 * @param fieldToCheckValue
	 * @param fieldToCheckName
	 * @param keyErrorMsg
	 * @param patternToCheck
	 */
	public static void validateNotEmptyAndFormat(
			Errors errors, String fieldToCheckValue, String fieldToCheckName, String keyErrorMsg, String patternToCheck) {
		if (StringUtils.isEmpty(fieldToCheckValue)) {
			errors.rejectValue(fieldToCheckName, "field_required");
		} else {
			Pattern pattern = Pattern.compile(patternToCheck);
			Matcher matcher = pattern.matcher(fieldToCheckValue);
			if (!matcher.matches()){
				errors.rejectValue(fieldToCheckName, keyErrorMsg);
			}
		}
	}
}
