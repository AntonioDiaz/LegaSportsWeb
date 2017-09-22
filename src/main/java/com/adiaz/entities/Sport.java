package com.adiaz.entities;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class Sport implements Serializable {

	//	private static final Logger log = Logger.getLogger(Sport.class.getName());
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Index
	private String name;

	@Index
	private String tag;

	public Sport() {
		super();
	}	
	
	public Sport(String name, String tag) {
		super();
		this.name = name;
		this.tag = tag;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
