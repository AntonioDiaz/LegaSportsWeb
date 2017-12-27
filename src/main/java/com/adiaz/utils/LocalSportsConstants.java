package com.adiaz.utils;


import java.io.Serializable;

public class LocalSportsConstants {

	public static final String PHONE_PATTERN = "^(\\d{9})$";
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String USERNAME_PATTERN = "[a-zA-Z|\\.|ñ|Ñ]{4,20}";
	public static final String HEXADECIMAL_PATTERN = "^#[0-9A-F]{6}";

	public static final String[][] SPORTS_NAMES = new String[][]{
			{"Futbol 11", "FUTBOL_11"},
			{"Futbol 7", "FUTBOL_7"},
			{"Futbol Sala", "FUTBOL_SALA"},
			{"Baloncesto", "BALONCESTO"},
			{"Balonmano", "BALONMANO"},
			{"Voleybol", "VOLEYBOL"},
			{"Unihockey", "UNIHOCKEY"}};


	public static final String BASKET = SPORTS_NAMES[3][0];

	public static final String FEMENINO = "Femenino";
	public static final String MASCULINO = "Masculino";
	public static final String MIXTO = "Mixto";

	public static final String BENJAMIN = "Benjamín";
	public static final String ALEVIN = "Alevín";
	public static final String INFANTIL = "Infantíl";
	public static final String CADETE = "Cadete";
	public static final String JUVENIL = "Juvenil";
	public static final String ABSOLUTA = "Absoluta";
	public static final String VETERANO = "Veterano";

	public static final String[] CATEGORIES_NAMES = new String[]{
			BENJAMIN + " " + FEMENINO,
			BENJAMIN + " " + MASCULINO,
			BENJAMIN + " " + MIXTO,
			ALEVIN + " " + FEMENINO,
			ALEVIN + " " + MASCULINO,
			ALEVIN + " " + MIXTO,
			INFANTIL + " " + FEMENINO,
			INFANTIL + " " + MASCULINO,
			INFANTIL + " " + MIXTO,
			CADETE + " " + FEMENINO,
			CADETE + " " + MASCULINO,
			JUVENIL,
			ABSOLUTA,
			VETERANO
	};
	public static final String TOWN_LEGANES = "Leganés";
	public static final String TOWN_FUENLABRADA = "Fuenlabrada";
	public static final int MAX_ISSUES_PER_CLIENT_AND_DAY = 10;
	public static final int MAX_ISSUES_PER_DAY = 100;

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

	public static final short MATCH_STATE_PENDING = MATCH_STATE.PENDING.getValue();
	public static final short MATCH_STATE_PLAYED = MATCH_STATE.PLAYED.getValue();
	public static final short MATCH_STATE_CANCELED = MATCH_STATE.CANCELED.getValue();

	public static final int POINTS_WON = 3;
	public static final int POINTS_DRAWN = 1;
	public static final int POINTS_LOST = 0;
}
