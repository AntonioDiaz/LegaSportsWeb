package com.adiaz.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adiaz.entities.*;
import com.adiaz.forms.MatchForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;

public class LocalSportsUtils {

	private static final Logger logger = Logger.getLogger(LocalSportsUtils.class.getName());
	public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String DATE_ZONE_MADRID = "Europe/Madrid";

	public static Set<String> parseCalendarGetTeams() {
		Set<String> teamsNames = new HashSet<>();
		String lines = parseLinesTextFile("static_calendar.txt");
		String[] split = lines.split("\\r\\n");
		for (int i = 0; i < split.length; i++) {
			String[] strings = split[i].split("\\t");
			if (strings.length>=3) {
				teamsNames.add(strings[2]);
				teamsNames.add(strings[3]);
			}
		}
		return teamsNames;
	}

	public static List<MatchForm> parseCalendarGetMatches() {
		String lines = parseLinesTextFile("static_calendar.txt");
		List<MatchForm> matchesList = new ArrayList<>();
		String[] split = lines.split("\\r\\n");
		int week = 0;
		for (int i = 0; i < split.length; i++) {
			String line = split[i];
			if (line.startsWith("Jornada")) {
				week++;
			} else {
				String[] strings = line.split("\\t");
				MatchForm match = new MatchForm();
				match.setWeek(week);
				match.setTeamLocalName(strings[2]);
				match.setTeamVisitorName(strings[3]);
				match.setDateStr(strings[0] + " " + strings[1]);
				matchesList.add(match);
			}
		}
		return matchesList;
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

	public static User getActiveUser(){
		User user = null;
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return user;
	}

	private static String parseLinesTextFile(String fileName) {
		String calendarTxt = "";
		ClassLoader classLoader = LocalSportsUtils.class.getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
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

	public static Date calculateLastMidnight(){
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date calculateNextMidnigth(){
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static final String parseDateToString(Date date) {
		String dateStr = null;
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(DATE_ZONE_MADRID));
		if (date!=null) {
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	public static final Date parseStringToDate(String dateStr){
		Date date = null;
		if (StringUtils.isNotBlank(dateStr)) {
			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			dateFormat.setTimeZone(TimeZone.getTimeZone(DATE_ZONE_MADRID));
			try {
				date = dateFormat.parse(dateStr);
			} catch (ParseException e) {
				logger.error("error on parse form to formToEntity " + dateStr, e);
			}
		}
		return date;
	}
}
