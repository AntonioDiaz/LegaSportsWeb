package com.adiaz.entities;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Sport implements Serializable {

	//	private static final Logger log = Logger.getLogger(Sport.class.getName());
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Index
	private String name;

	public Sport() {
		super();
	}	
	
	public Sport(String name) {
		super();
		this.name = name;
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
}
