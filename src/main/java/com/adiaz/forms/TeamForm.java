package com.adiaz.forms;

/**
 * Created by toni on 25/07/2017.
 */
public class TeamForm {

	private Long id;
	private String name;
	private Long idCategory;
	private Long idTown;
	private Long idClub;
	private Long idSport;

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

	public Long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}

	public Long getIdTown() {
		return idTown;
	}

	public void setIdTown(Long idTown) {
		this.idTown = idTown;
	}

	public Long getIdClub() {
		return idClub;
	}

	public void setIdClub(Long idClub) {
		this.idClub = idClub;
	}

	public Long getIdSport() {
		return idSport;
	}

	public void setIdSport(Long idSport) {
		this.idSport = idSport;
	}
}
