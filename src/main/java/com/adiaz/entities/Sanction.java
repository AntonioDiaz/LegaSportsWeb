package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.Data;

/**
 * Created by toni on 28/09/2017.
 */
@Entity
@Data
public class Sanction {
	@Id
	private Long id;

	private Integer points;

	private String description;

	@Load
	@JsonIgnore
	@Index
	private Ref<Team> teamRef;

	@Load
	@JsonIgnore
	@Index
	private Ref<Competition> competitionRef;

	@Ignore
	@JsonIgnore
	private Team team;

	@Ignore
	@JsonIgnore
	private Competition competition;

	@OnLoad
	public void getRefs(){
		if (teamRef!=null && teamRef.isLoaded()){
			team = teamRef.get();
		}
		if (competitionRef!=null && competitionRef.isLoaded()) {
			competition = competitionRef.get();
		}
	}



}
