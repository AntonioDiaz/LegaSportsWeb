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
public class CompetitionsVO {

	@Id
	private Long id;
	
	@Index
	private String name;
	
	@Load
	@JsonIgnore
	@Index
	private Ref<Sport> sport;
	
	@Load
	@JsonIgnore
	@Index
	private Ref<Category> category;
	
	
	/* start: attributes not need to save. */
	@Ignore
	private Sport sportEntity;
	
	@Ignore
	private Category categoryEntity;
	
	@Ignore
	private List<MatchesVO> matches;
	
	@Ignore
	private List<ClassificationEntryVO> classification;
	/* end: attributes not need to save. */
	
	@OnLoad
	public void getRefs() {
		if (sport!=null && sport.isLoaded()) {
			sportEntity = sport.get();
		}
		if (category!=null && category.isLoaded()) {
			categoryEntity = category.get();
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

	public Ref<Sport> getSport() {
		return sport;
	}

	public void setSport(Ref<Sport> sport) {
		this.sport = sport;
	}

	public Ref<Category> getCategory() {
		return category;
	}

	public void setCategory(Ref<Category> category) {
		this.category = category;
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

	public List<MatchesVO> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchesVO> matches) {
		this.matches = matches;
	}

	public List<ClassificationEntryVO> getClassification() {
		return classification;
	}

	public void setClassification(List<ClassificationEntryVO> classification) {
		this.classification = classification;
	}
}
