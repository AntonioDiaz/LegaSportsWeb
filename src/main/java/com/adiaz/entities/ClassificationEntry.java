package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Cache
@Data
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

}
