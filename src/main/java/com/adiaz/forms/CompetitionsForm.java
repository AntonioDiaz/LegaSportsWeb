package com.adiaz.forms;


public class CompetitionsForm {
	
	private Long sportId;
	private Long categoryId;
	private String name;
	
	public Long getSportId() {
		return sportId;
	}
	public void setSportId(Long sportId) {
		this.sportId = sportId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
