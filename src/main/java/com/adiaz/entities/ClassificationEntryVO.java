package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class ClassificationEntryVO {

	@Id
	private Long id;

	@Index
	@Load
	@JsonIgnore
	private Ref<CompetitionsVO> competitionRef;

	@Index
	private Integer position;	
	
	@Index
	private String team;
	
	private Integer points;
	private Integer matchesPlayed;
	private Integer matchesWon;
	private Integer matchesDrawn;
	private Integer matchesLost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ref<CompetitionsVO> getCompetitionRef() {
		return competitionRef;
	}

	public void setCompetitionRef(Ref<CompetitionsVO> competition) {
		this.competitionRef = competition;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
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

	
	
	
}
