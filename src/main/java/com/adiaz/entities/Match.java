package com.adiaz.entities;

import java.io.Serializable;
import java.util.Date;

import com.adiaz.utils.LocalSportsUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;


@Entity
@Cache
public class Match implements Serializable {

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
	@Index
	private Ref<Court> courtRef;

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
	@JsonProperty ("sportCenterCourt")
	private Court court;

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
		if (courtRef !=null && courtRef.isLoaded()) {
			court = courtRef.get();
		}
		if (matchPublishedRef!=null && matchPublishedRef.isLoaded() && matchPublishedRef.get()!=null) {
			matchPublished = matchPublishedRef.get();
		}
	}

	@JsonIgnore
	public String getFullName(){
		String localTeamStr = this.getTeamLocalEntity()!=null?this.getTeamLocalEntity().getName():"_";
		String visitorTeamStr = this.getTeamVisitorEntity()!=null?this.getTeamVisitorEntity().getName():"_";
		return String.format("Week %1$s (%2$s - %3$s)", this.getWeek(), localTeamStr, visitorTeamStr);
	}

	@JsonIgnore
	public String getFullDescription(){
		String matchDesc = this.getFullName();
		matchDesc  += "  Date: ";
		if (this.getDate()!=null) {
			matchDesc += LocalSportsUtils.parseDateToString(this.getDate());
		} else {
			matchDesc += "_";
		}
		matchDesc += "  Centro: ";
		if (this.getCourt()!=null) {
			matchDesc += this.getCourt().getNameWithCenter();
		} else {
			matchDesc += "_";
		}
		return matchDesc;
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


	public Ref<Court> getCourtRef() {
		return courtRef;
	}

	public void setCourtRef(Ref<Court> courtRef) {
		this.courtRef = courtRef;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
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
