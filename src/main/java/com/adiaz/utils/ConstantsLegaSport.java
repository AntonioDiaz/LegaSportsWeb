package com.adiaz.utils;


public class ConstantsLegaSport {

	public static final String PHONE_PATTERN = "^(\\d{9})$";
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String USERNAME_PATTERN = "[a-zA-Z|\\.|ñ|Ñ]{4,20}";


	public static String[] SPORTS_NAMES = new String[]{
		"FUTBOL_11", "FUTBOL_7", "FUTBOL_SALA", "BALONCESTO", "BALONMANO", "VOLEIBOL", "UNIHOCKEY"
	};

	
	public static String[] CATEGORIES_NAMES = new String[]{
		"Benjamin", "Alevin", "Infantil", "Cadete", "Juvenil", "Absoluta", "Veterano"
	};	
	
	
	
}
