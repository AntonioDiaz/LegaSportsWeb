package com.adiaz.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.adiaz.utils.Deref;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.Data;


// TODO: 20/09/2017 rename Court to Court.
@Entity
@Cache
@Data
public class Court implements Serializable {

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
	private Ref<Center> centerRef;

	@Ignore
	@JsonProperty("sportCenter")
	private Center center;

	@OnLoad
	public void getRefs() {
		if (centerRef !=null && centerRef.isLoaded()) {
			center = centerRef.get();
		}
	}

	@JsonIgnore
	public List<Sport> getSportsDeref() {
		return Deref.deref(sports); 
	}

	public String getNameWithCenter() {
		String longName = "";
		if (center !=null) {
			longName += center.getName() + " - ";
		}
		longName += name;
		return longName;
	}
}
