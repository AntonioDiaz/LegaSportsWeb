package com.adiaz.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.adiaz.utils.Deref;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;


// TODO: 20/09/2017 rename SportCenterCourt to Court.
@Entity
public class SportCenterCourt implements Serializable {

	@Id
	private Long id;
	
	private String name;
	
	@Load
	@JsonIgnore
	@Index
	private List<Ref<Sport>> sports = new ArrayList<Ref<Sport>>();

	@Load
	@Index
	@JsonIgnore
	private Ref<SportCenter> sportCenterRef;

	@Ignore
	private SportCenter sportCenter;

	@OnLoad
	public void getRefs() {
		if (sportCenterRef!=null && sportCenterRef.isLoaded()) {
			sportCenter = sportCenterRef.get();
		}
	}

	@JsonIgnore
	public List<Sport> getSportsDeref() {
		return Deref.deref(sports); 
	}

	public String getNameWithCenter() {
		String longName = "";
		if (sportCenter!=null) {
			longName += sportCenter.getName() + " - ";
		}
		longName += name;
		return longName;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Ref<Sport>> getSports() {
		return sports;
	}


	public void setSports(List<Ref<Sport>> sports) {
		this.sports = sports;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Ref<SportCenter> getSportCenterRef() {
		return sportCenterRef;
	}


	public void setSportCenterRef(Ref<SportCenter> sportCenterRef) {
		this.sportCenterRef = sportCenterRef;
	}

	public SportCenter getSportCenter() {
		return sportCenter;
	}

	public void setSportCenter(SportCenter sportCenter) {
		this.sportCenter = sportCenter;
	}



}
