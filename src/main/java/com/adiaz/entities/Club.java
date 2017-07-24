package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

/**
 * Created by toni on 24/07/2017.
 */
@Entity
public class Club {
	@Id
	private Long id;

	private String name;

	private String contactPerson;

	private String contactEmail;

	private String conctactAddress;

	private String concatctPhone;

	@Load
	@JsonIgnore
	@Index
	private Ref<Town> townRef;

	@Ignore
	@JsonIgnore
	private Town town;

	@OnLoad
	private void getRefs(){
		if (townRef!=null && townRef.isLoaded()){
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

	public String getConctactAddress() {
		return conctactAddress;
	}

	public void setConctactAddress(String conctactAddress) {
		this.conctactAddress = conctactAddress;
	}

	public String getConcatctPhone() {
		return concatctPhone;
	}

	public void setConcatctPhone(String concatctPhone) {
		this.concatctPhone = concatctPhone;
	}

	public Ref<Town> getTownRef() {
		return townRef;
	}

	public void setTownRef(Ref<Town> townRef) {
		this.townRef = townRef;
	}
}
