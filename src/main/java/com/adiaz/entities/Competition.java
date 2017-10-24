package com.adiaz.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adiaz.utils.Deref;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.Data;

@Entity
@Cache
@Data
public class Competition implements Serializable {

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

	@Load
	@JsonIgnore
	@Index
	private List<Ref<Team>> teams = new ArrayList<>();

	@Load
	@JsonIgnore
	private List<Ref<Team>> teamsAffectedByPublish = new ArrayList<>();

	@Index
	private Date lastPublished;


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

	/**
	 * generate competition name with the format: name (sport - category)
	 * @return
	 */
	public String getFullName(){
		String sportName = sportEntity!=null?sportEntity.getName():"";
		String categoryName = categoryEntity!=null?categoryEntity.getName():"";
		return String.format("%1$s (%2$s - %3$s)", name, sportName, categoryName);
	}

	public List<Team> getTeamsDeref() {
		return Deref.deref(teams);
	}

	public List<Team> getTeamsAffectedByLastUpdateDeref() {
		return Deref.deref(teamsAffectedByPublish);
	}

}
