package com.adiaz.entities;

import com.adiaz.controllers.CompetitionsController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import org.apache.log4j.Logger;

/**
 * Created by toni on 25/07/2017.
 */
@Entity
public class Team {

	private static final Logger logger = Logger.getLogger(Team.class);

	@Id
	private Long id;

	@Index
	private String name;

	@Load
	@Index
	@JsonIgnore
	private Ref<Category> categoryRef;

	@Ignore
	@JsonIgnore
	private Category categoryEntity;

	@Load
	@Index
	@JsonIgnore
	private Ref<Town> townRef;

	@Ignore
	@JsonIgnore
	private Town townEntity;

	@Load
	@Index
	@JsonIgnore
	private Ref<Club> clubRef;

	@Ignore
	@JsonIgnore
	private Club clubEntity;

	@Load
	@Index
	@JsonIgnore
	private Ref<Sport> sportRef;

	@Ignore
	@JsonIgnore
	private Sport sportEntity;

	@OnLoad
	public void getRefs() {
		if (categoryRef!=null && categoryRef.isLoaded()) {
			categoryEntity = categoryRef.get();
		}
		if (clubRef!=null && clubRef.isLoaded()) {
			clubEntity = clubRef.get();
		}
		if (townRef!=null && townRef.isLoaded()) {
			townEntity = townRef.get();
		}
		if (sportRef!=null && sportRef.isLoaded()) {
			sportEntity = sportRef.get();
		}
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

	public Ref<Category> getCategoryRef() {
		return categoryRef;
	}

	public void setCategoryRef(Ref<Category> categoryRef) {
		this.categoryRef = categoryRef;
	}

	public Category getCategoryEntity() {
		return categoryEntity;
	}

	public void setCategoryEntity(Category categoryEntity) {
		this.categoryEntity = categoryEntity;
	}

	public Ref<Club> getClubRef() {
		return clubRef;
	}

	public void setClubRef(Ref<Club> clubRef) {
		this.clubRef = clubRef;
	}

	public Club getClubEntity() {
		return clubEntity;
	}

	public void setClubEntity(Club clubEntity) {
		this.clubEntity = clubEntity;
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

	public Ref<Sport> getSportRef() {
		return sportRef;
	}

	public void setSportRef(Ref<Sport> sportRef) {
		this.sportRef = sportRef;
	}

	public Sport getSportEntity() {
		return sportEntity;
	}

	public void setSportEntity(Sport sportEntity) {
		this.sportEntity = sportEntity;
	}
}
