package com.adiaz.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adiaz.utils.Deref;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

@Entity
public class Competition {

	@Id
	private Long id;
	
	@Index
	private String name;
	
	@Load
	@JsonIgnore
	@Index
	private Ref<Sport> sportRef;
	
	@Load
	@JsonIgnore
	@Index
	private Ref<Category> categoryRef;

	@Load
	@JsonIgnore
	@Index
	private Ref<Town> townRef;

	/* start: attributes not need to save. */
	@Ignore
	private Sport sportEntity;
	
	@Ignore
	private Category categoryEntity;

	@Ignore
	private Town townEntity;

	@Ignore
	private List<Match> matches;
	
	@Ignore
	private List<ClassificationEntry> classification;

	@Load
	@JsonIgnore
	private List<Ref<Team>> teams = new ArrayList<Ref<Team>>();

	private Date lastUpdate;


	@OnLoad
	public void getRefs() {
		if (sportRef !=null && sportRef.isLoaded()) {
			sportEntity = sportRef.get();
		}
		if (categoryRef !=null && categoryRef.isLoaded()) {
			categoryEntity = categoryRef.get();
		}
		if (townRef!=null && townRef.isLoaded()) {
			townEntity = townRef.get();
		}
	}

	public List<Team> getTeamsDeref() {
		return Deref.deref(teams);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ref<Sport> getSportRef() {
		return sportRef;
	}

	public void setSportRef(Ref<Sport> sportRef) {
		this.sportRef = sportRef;
	}

	public Ref<Category> getCategoryRef() {
		return categoryRef;
	}

	public void setCategoryRef(Ref<Category> categoryRef) {
		this.categoryRef = categoryRef;
	}

	public Sport getSportEntity() {
		return sportEntity;
	}

	public void setSportEntity(Sport sportEntity) {
		this.sportEntity = sportEntity;
	}

	public Category getCategoryEntity() {
		return categoryEntity;
	}

	public void setCategoryEntity(Category categoryEntity) {
		this.categoryEntity = categoryEntity;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public List<ClassificationEntry> getClassification() {
		return classification;
	}

	public void setClassification(List<ClassificationEntry> classification) {
		this.classification = classification;
	}

	public Ref<Town> getTownRef() {
		return townRef;
	}

	public void setTownRef(Ref<Town> townRef) {
		this.townRef = townRef;
	}

	public Town getTownEntity() {
		return townEntity;
	}

	public void setTownEntity(Town townEntity) {
		this.townEntity = townEntity;
	}

	public List<Ref<Team>> getTeams() {
		return teams;
	}

	public void setTeams(List<Ref<Team>> teams) {
		this.teams = teams;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
