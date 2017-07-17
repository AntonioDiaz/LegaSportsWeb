package com.adiaz.entities;

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
	private Ref<Town> townRef;

	@Ignore
	private Town town;

	@OnLoad
	public void getRefs(){
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

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}
}
