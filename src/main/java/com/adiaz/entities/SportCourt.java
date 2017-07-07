package com.adiaz.entities;

import java.util.ArrayList;
import java.util.List;

import com.adiaz.utils.Deref;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;


@Entity
public class SportCourt {

	@Id
	private Long id;
	
	private String name;
	
	@Load
	private List<Ref<SportVO>> sports = new ArrayList<Ref<SportVO>>();

	@Load
	@Index
	private Ref<SportCenter> center; 
	

	public List<SportVO> getSportsDeref() { 
		return Deref.deref(sports); 
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Ref<SportVO>> getSports() {
		return sports;
	}


	public void setSports(List<Ref<SportVO>> sports) {
		this.sports = sports;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Ref<SportCenter> getCenter() {
		return center;
	}


	public void setCenter(Ref<SportCenter> center) {
		this.center = center;
	}
	
}
