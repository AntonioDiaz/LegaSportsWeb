package com.adiaz.entities;

import java.util.List;

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
	
	
	/* start: attributes not need to save. */
	@Ignore
	private Sport sportEntity;
	
	@Ignore
	private Category categoryEntity;
	
	@Ignore
	private List<Match> matches;
	
	@Ignore
	private List<ClassificationEntry> classification;

	@Load
	@JsonIgnore
	@Index
	private Ref<Town> townRef;

	@Ignore
	private Town town;
	
	@OnLoad
	public void getRefs() {
		if (sportRef !=null && sportRef.isLoaded()) {
			sportEntity = sportRef.get();
		}
		if (categoryRef !=null && categoryRef.isLoaded()) {
			categoryEntity = categoryRef.get();
		}
		if (townRef!=null && townRef.isLoaded()) {
			town = townRef.get();
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

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}
}
