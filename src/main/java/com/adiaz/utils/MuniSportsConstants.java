package com.adiaz.utils;


import java.io.Serializable;

public class MuniSportsConstants {

	public static final String PHONE_PATTERN = "^(\\d{9})$";
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String USERNAME_PATTERN = "[a-zA-Z|\\.|ñ|Ñ]{4,20}";

	public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

	public static final String[][] SPORTS_NAMES = new String[][]{
			{"Futbol 11", "FUTBOL_11"},
			{"Futbol 7", "FUTBOL_7"},
			{"Futbol Sala", "FUTBOL_SALA"},
			{"Baloncesto", "BALONCESTO"},
			{"Balonmano", "BALONMANO"},
			{"Voleybol", "VOLEYBOL"},
			{"Unihockey", "UNIHOCKEY"}};


	public static final String BASKET = SPORTS_NAMES[3][0];

	public static final String[] CATEGORIES_NAMES = new String[]{
			"Benjamin", "Alevin", "Infantil", "Cadete", "Juvenil", "Absoluta", "Veterano"
	};

	public enum MATCH_STATE {
		PENDING((short)0, "Pendiente"), PLAYED((short)1, "Jugado"), CANCELED((short)2, "Cancelado");
		private short value;
		private String stateDesc;
		MATCH_STATE(short i, String stateDesc) {
			this.value = i;
			this.stateDesc = stateDesc;
		}

		public short getValue() {
			return value;
		}

		public String getStateDesc() {
			return stateDesc;
		}
	}

	public static short MATCH_STATE_PENDING = MATCH_STATE.PENDING.getValue();
	public static short MATCH_STATE_PLAYED = MATCH_STATE.PLAYED.getValue();
	public static short MATCH_STATE_CANCELED = MATCH_STATE.CANCELED.getValue();


}
