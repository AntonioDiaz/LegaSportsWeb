package com.adiaz.entities;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CategoriesVO implements Serializable {

	//private static final Logger log = Logger.getLogger(CategoriesVO.class.getName());

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@Index
	private String name;
	
	@Index
	private Integer order;	

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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
