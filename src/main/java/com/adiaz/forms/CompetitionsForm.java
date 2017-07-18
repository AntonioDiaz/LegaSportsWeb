package com.adiaz.forms;


public class CompetitionsForm {

	private String name;
	private Long sportId;
	private Long categoryId;
	private Long townId;

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

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}
}
