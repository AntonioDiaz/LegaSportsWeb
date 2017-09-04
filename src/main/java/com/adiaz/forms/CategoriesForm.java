package com.adiaz.forms;

/**
 * Created by toni on 04/09/2017.
 */
public class CategoriesForm {
	Long id;
	Integer order;
	String name;

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
