package com.adiaz.utils;


public class MuniSportsConstants {

	public static final String PHONE_PATTERN = "^(\\d{9})$";
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String USERNAME_PATTERN = "[a-zA-Z|\\.|ñ|Ñ]{4,20}";

	public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

	public static final String[] SPORTS_NAMES = new String[]{
		"Futbol 11", "Futbol 7", "Futbol Sala", "Baloncesto", "Balonmano", "Voleybol", "Unihockey"
	};


	public static final String BASKET = SPORTS_NAMES[3];

	public static final String[] CATEGORIES_NAMES = new String[]{
		"Benjamin", "Alevin", "Infantil", "Cadete", "Juvenil", "Absoluta", "Veterano"
	};	
	
	
	
}
