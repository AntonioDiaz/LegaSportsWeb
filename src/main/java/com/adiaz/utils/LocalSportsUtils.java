package com.adiaz.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adiaz.entities.*;
import com.adiaz.forms.MatchForm;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.JsonParser;
import com.google.appengine.repackaged.com.google.gson.JsonSyntaxException;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
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

	public static long sendNotificationToFirebase(Competition competition) {
		try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("competition", competition);
            JSONObject jsonRoot = new JSONObject();
            jsonRoot.put("to", "/topics/" + competition.getTownEntity().getTopicName());
            jsonRoot.put("data", jsonData);
			URL url = new URL(LocalSportsConstants.FCM_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json charset=UTF-8");
            conn.setRequestProperty("Authorization", "key=" + LocalSportsConstants.FCM_SERVER_KEY);
            conn.getOutputStream().write(jsonRoot.toString().getBytes());
            logger.debug("FCM json: " + jsonRoot.toString());
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.close();
            logger.debug("FCM respCode: " + conn.getResponseCode());
            long respCode = -1;
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try {
                    StringBuffer response = new StringBuffer();
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    logger.debug("FCM response body: " + response);
                    JsonParser parser = new JsonParser();
                    JsonObject jsonResponse = parser.parse(response.toString()).getAsJsonObject();
                    respCode = jsonResponse.get("message_id").getAsLong();
                } catch (IOException | JsonSyntaxException e) {
                    logger.error("FCM Error on send notification: " + e.getLocalizedMessage(), e);
                }
            }
            return respCode;
        } catch (IOException | JSONException e) {
			logger.error("FCM Error on send notification: " + e.getLocalizedMessage(), e);
			return -1;
        }
    }

}
