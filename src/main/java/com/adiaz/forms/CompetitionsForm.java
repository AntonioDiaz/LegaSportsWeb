package com.adiaz.forms;


public class CompetitionsForm {

	private Long id;
	private String name;
	private Long idSport;
	private Long idCategory;
	private Long idTown;
	private Long[] teams;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdSport() {
		return idSport;
	}
	public void setIdSport(Long idSport) {
		this.idSport = idSport;
	}
	public Long getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getIdTown() {
		return idTown;
	}

	public void setIdTown(Long idTown) {
		this.idTown = idTown;
	}

	public Long[] getTeams() {
		return teams;
	}

	public void setTeams(Long[] teams) {
		this.teams = teams;
	}
}
