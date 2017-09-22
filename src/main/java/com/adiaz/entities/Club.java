package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.io.Serializable;

/**
 * Created by toni on 24/07/2017.
 */
@Entity
@Cache
public class Club  implements Serializable {
	@Id
	private Long id;

	private String name;

	private String contactPerson;

	private String contactEmail;

	private String contactAddress;

	private String contactPhone;

	@Load
	@JsonIgnore
	@Index
	private Ref<Town> townRef;

	@Ignore
	@JsonIgnore
	private Town townEntity;

	@OnLoad
	private void getRefs(){
		if (townRef!=null && townRef.isLoaded()){
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
