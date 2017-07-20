package com.adiaz.entities;

import java.util.ArrayList;
import java.util.List;

import com.adiaz.utils.Deref;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;


@Entity
public class SportCourt {

	@Id
	private Long id;
	
	private String name;
	
	@Load
	private List<Ref<Sport>> sports = new ArrayList<Ref<Sport>>();

	@Load
	@Index
	@JsonIgnore
	private Ref<SportCenter> sportCenterRef;

	@Ignore
	@JsonIgnore
	private SportCenter sportCenter;

	@OnLoad
	public void getRefs() {
		if (sportCenterRef!=null && sportCenterRef.isLoaded()) {
			sportCenter = sportCenterRef.get();
		}
	}

		public List<Sport> getSportsDeref() {
		return Deref.deref(sports); 
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
