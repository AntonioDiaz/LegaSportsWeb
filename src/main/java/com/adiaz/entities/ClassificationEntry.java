package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.io.Serializable;

@Entity
@Cache
public class ClassificationEntry  implements Serializable {

	@Id
	private Long id;

	@Index
	@Load
	@JsonIgnore
	private Ref<Competition> competitionRef;

	@Ignore
	@JsonIgnore
	Competition competition;

	@Index
	@Load
	@JsonIgnore
	private Ref<Team> teamRef;

	@Ignore
	@JsonIgnore
	private Team teamEntity;

	@Index
	private Integer position;	
	
	private Integer points;
	private Integer matchesPlayed;
	private Integer matchesWon;
	private Integer matchesDrawn;
	private Integer matchesLost;
	private Integer goalsFor;
	private Integer goalsAgainst;
	private Integer sanctions;


	private String team;

	@OnLoad
	public void getRefs() {
		if (teamRef!=null && teamRef.isLoaded() ) {
			teamEntity = teamRef.get();
			team = teamRef.get().getName();
		}
		if (competitionRef!=null && competitionRef.isLoaded()){
			competition = competitionRef.get();
		}
	}

	public Integer calculateRealPoints() {
		return points - sanctions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ref<Competition> getCompetitionRef() {
		return competitionRef;
	}

	public void setCompetitionRef(Ref<Competition> competition) {
		this.competitionRef = competition;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(Integer matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public Integer getMatchesWon() {
		return matchesWon;
	}

	public void setMatchesWon(Integer matchesWon) {
		this.matchesWon = matchesWon;
	}

	public Integer getMatchesDrawn() {
		return matchesDrawn;
	}

	public void setMatchesDrawn(Integer matchesDrawn) {
		this.matchesDrawn = matchesDrawn;
	}

	public Integer getMatchesLost() {
		return matchesLost;
	}

	public void setMatchesLost(Integer matchesLost) {
		this.matchesLost = matchesLost;
	}

	public Ref<Team> getTeamRef() {
		return teamRef;
	}

	public void setTeamRef(Ref<Team> teamRef) {
		this.teamRef = teamRef;
	}

	public Team getTeamEntity() {
		return teamEntity;
	}

	public void setTeamEntity(Team teamEntity) {
		this.teamEntity = teamEntity;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public Integer getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(Integer goalsFor) {
		this.goalsFor = goalsFor;
	}

	public Integer getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(Integer goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public Integer getSanctions() {
		return sanctions;
	}

	public void setSanctions(Integer sanctions) {
		this.sanctions = sanctions;
	}
}
