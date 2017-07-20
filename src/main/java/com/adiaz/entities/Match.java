package com.adiaz.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.adiaz.utils.ConstantsLegaSport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;


@Entity
public class Match {

	@Id
	private Long id;
	
	@Index
	private String teamLocal;
	
	@Index
	private String teamVisitor;
	
	@Index
	private Date date;

	@Load
	@JsonIgnore
	private Ref<SportCourt> sportCourtRef;

	private int scoreLocal;
	
	private int scoreVisitor;
	
	@Index
	private int week;
	
	@Index
	@Load
	@JsonIgnore
	private Ref<Competition> competitionRef;
	
	@Ignore
	@JsonIgnore
	private Competition competition;

	@Ignore
	private String dateStr;

	@Ignore
	private SportCourt sportCourt;


	@OnLoad
	public void getRefs() {
		if (competitionRef!=null && competitionRef.isLoaded()) {
			competition = competitionRef.get();
		}
		if (date!=null) {
			DateFormat dateFormat = new SimpleDateFormat(ConstantsLegaSport.DATE_FORMAT);
			dateStr = dateFormat.format(date);
		}
		if (sportCourtRef!=null && sportCourtRef.isLoaded()) {
			sportCourt = sportCourtRef.get();
		}
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTeamLocal() {
		return teamLocal;
	}


	public void setTeamLocal(String teamLocal) {
		this.teamLocal = teamLocal;
	}


	public String getTeamVisitor() {
		return teamVisitor;
	}


	public void setTeamVisitor(String teamVisitor) {
		this.teamVisitor = teamVisitor;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getScoreLocal() {
		return scoreLocal;
	}


	public void setScoreLocal(int scoreLocal) {
		this.scoreLocal = scoreLocal;
	}


	public int getScoreVisitor() {
		return scoreVisitor;
	}


	public void setScoreVisitor(int scoreVisitor) {
		this.scoreVisitor = scoreVisitor;
	}


	public Ref<Competition> getCompetitionRef() {
		return competitionRef;
	}


	public void setCompetitionRef(Ref<Competition> competitionRef) {
		this.competitionRef = competitionRef;
	}


	public Competition getCompetition() {
		return competition;
	}


	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Ref<SportCourt> getSportCourtRef() {
		return sportCourtRef;
	}

	public void setSportCourtRef(Ref<SportCourt> sportCourtRef) {
		this.sportCourtRef = sportCourtRef;
	}

	public SportCourt getSportCourt() {
		return sportCourt;
	}

	public void setSportCourt(SportCourt sportCourt) {
		this.sportCourt = sportCourt;
	}
}
