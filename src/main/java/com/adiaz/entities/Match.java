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
	private Ref<SportCenterCourt> sportCenterCourtRef;

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
	private Long courtId;

	@Ignore
	private SportCenterCourt sportCenterCourt;

	@Index
	private boolean workingCopy;


	@Load
	@JsonIgnore
	private Ref<Match> matchPublishedRef;

	@Ignore
	@JsonIgnore
	private Match matchPublished;


	@Ignore
	private boolean updatedDate;

	@Ignore
	private boolean updatedScore;

	@Ignore
	private boolean updatedCourt;

	@OnLoad
	public void getRefs() {
		if (competitionRef!=null && competitionRef.isLoaded()) {
			competition = competitionRef.get();
		}
		if (date!=null) {
			DateFormat dateFormat = new SimpleDateFormat(ConstantsLegaSport.DATE_FORMAT);
			dateStr = dateFormat.format(date);
		}
		if (sportCenterCourtRef !=null && sportCenterCourtRef.isLoaded()) {
			sportCenterCourt = sportCenterCourtRef.get();
			if (sportCenterCourtRef.get()!=null) {
				courtId = sportCenterCourtRef.get().getId();
			}
		}
		if (matchPublishedRef!=null && matchPublishedRef.isLoaded()) {
			matchPublished = matchPublishedRef.get();
			if (this.scoreLocal!=matchPublished.getScoreLocal() || this.scoreVisitor!=matchPublished.getScoreVisitor()) {
				updatedScore = true;
			}
			if ( date==null && matchPublished.getDate()!=null
					||date!=null && matchPublished.getDate()==null
					|| date!=null && matchPublished.getDate()!=null && !date.equals(matchPublished.getDate())) {
				updatedDate = true;
			}
			if (courtId!=matchPublished.getCourtId()) {
				updatedCourt = true;
			}
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

	public Ref<SportCenterCourt> getSportCenterCourtRef() {
		return sportCenterCourtRef;
	}

	public void setSportCenterCourtRef(Ref<SportCenterCourt> sportCenterCourtRef) {
		this.sportCenterCourtRef = sportCenterCourtRef;
	}

	public SportCenterCourt getSportCenterCourt() {
		return sportCenterCourt;
	}

	public void setSportCenterCourt(SportCenterCourt sportCenterCourt) {
		this.sportCenterCourt = sportCenterCourt;
	}

	public Long getCourtId() {
		return courtId;
	}

	public void setCourtId(Long courtId) {
		this.courtId = courtId;
	}

	public boolean isWorkingCopy() {
		return workingCopy;
	}

	public void setWorkingCopy(boolean workingCopy) {
		this.workingCopy = workingCopy;
	}

	public Ref<Match> getMatchPublishedRef() {
		return matchPublishedRef;
	}

	public void setMatchPublishedRef(Ref<Match> matchPublishedRef) {
		this.matchPublishedRef = matchPublishedRef;
	}

	public Match getMatchPublished() {
		return matchPublished;
	}

	public void setMatchPublished(Match matchPublished) {
		this.matchPublished = matchPublished;
	}

	public boolean isUpdatedScore() {
		return updatedScore;
	}

	public void setUpdatedScore(boolean updatedScore) {
		this.updatedScore = updatedScore;
	}

	public boolean isUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(boolean updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isUpdatedCourt() {
		return updatedCourt;
	}

	public void setUpdatedCourt(boolean updatedCourt) {
		this.updatedCourt = updatedCourt;
	}
}
