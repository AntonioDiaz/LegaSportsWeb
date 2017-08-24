package com.adiaz.entities;

import java.util.Date;

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
	@Load
	@JsonIgnore
	private Ref<Team> teamLocalRef;
	
	@Index
	@Load
	@JsonIgnore
	private Ref<Team> teamVisitorRef;
	
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
	private SportCenterCourt sportCenterCourt;

	/** if workingCopy==false then this is the match published.	*/
	@Index
	private boolean workingCopy;


	/** matchPublishedRef is a reference to the match published, this only make sense when workingCopy==false.*/
	@Load
	@JsonIgnore
	private Ref<Match> matchPublishedRef;

	@Ignore
	@JsonIgnore
	private Match matchPublished;

	@Ignore
	private Team teamLocalEntity;

	@Ignore
	private Team teamVisitorEntity;

	/* match state: 0 pending, 1 played, 2 cancel. */
	private Short state;

	@OnLoad
	public void getRefs() {
		if (competitionRef!=null && competitionRef.isLoaded()) {
			competition = competitionRef.get();
		}
		if (teamLocalRef!=null && teamLocalRef.isLoaded()) {
			teamLocalEntity = teamLocalRef.get();
		}
		if (teamVisitorRef!=null && teamVisitorRef.isLoaded()) {
			teamVisitorEntity = teamVisitorRef.get();
		}
		if (sportCenterCourtRef !=null && sportCenterCourtRef.isLoaded()) {
			sportCenterCourt = sportCenterCourtRef.get();
		}
		if (matchPublishedRef!=null && matchPublishedRef.isLoaded() && matchPublishedRef.get()!=null) {
			matchPublished = matchPublishedRef.get();
		}
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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

	public Ref<Team> getTeamLocalRef() {
		return teamLocalRef;
	}

	public void setTeamLocalRef(Ref<Team> teamLocalRef) {
		this.teamLocalRef = teamLocalRef;
	}

	public Ref<Team> getTeamVisitorRef() {
		return teamVisitorRef;
	}

	public void setTeamVisitorRef(Ref<Team> teamVisitorRef) {
		this.teamVisitorRef = teamVisitorRef;
	}

	public Team getTeamLocalEntity() {
		return teamLocalEntity;
	}

	public void setTeamLocalEntity(Team teamLocalEntity) {
		this.teamLocalEntity = teamLocalEntity;
	}

	public Team getTeamVisitorEntity() {
		return teamVisitorEntity;
	}

	public void setTeamVisitorEntity(Team teamVisitorEntity) {
		this.teamVisitorEntity = teamVisitorEntity;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}
}
