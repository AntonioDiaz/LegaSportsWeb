package com.adiaz.entities;

import com.adiaz.utils.Deref;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toni on 14/07/2017.
 */
@Entity
@Cache
public class Town implements Serializable {

	@Id
	private Long id;

	@Index
	private String name;

	@JsonIgnore
	private String contactPerson;

	@JsonIgnore
	private String phone;

	@JsonIgnore
	private String email;

	@JsonIgnore
	private String address;

	@JsonIgnore
	private boolean isActive;

	private String iconName;

	@Load
	@JsonIgnore
	@Index
	private List<Ref<Sport>> sports = new ArrayList<>();

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}


	public List<Ref<Sport>> getSports() {
		return sports;
	}

	public void setSports(List<Ref<Sport>> sports) {
		this.sports = sports;
	}

	public List<Sport> getSportsDeref() {
		return Deref.deref(sports);
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
}
