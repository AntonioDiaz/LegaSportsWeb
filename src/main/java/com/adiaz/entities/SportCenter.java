package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;


@Entity
public class SportCenter {

	@Id
	private Long id;
	
	private String name;
	
	private String address;

	@Load
	@Index
	@JsonIgnore
	private Ref<Town> townRef;

	@Ignore
	private Town townEntity;

	@OnLoad
	public void getRefs(){
		if (townRef!=null && townRef.isLoaded()) {
			townEntity = townRef.get();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
}
