package com.adiaz.entities;

import java.util.ArrayList;
import java.util.List;

import com.adiaz.utils.Deref;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;


@Entity
public class SportCenter {

	@Id
	private Long id;
	
	private String name;
	
	private String address;
	
	@Load
	private List<Ref<SportCourt>> courts = new ArrayList<Ref<SportCourt>>();

	public List<SportCourt> getCourtsDeref() { 
		return Deref.deref(courts); 
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Ref<SportCourt>> getCourts() {
		return courts;
	}

	public void setCourts(List<Ref<SportCourt>> courts) {
		this.courts = courts;
	}
}
