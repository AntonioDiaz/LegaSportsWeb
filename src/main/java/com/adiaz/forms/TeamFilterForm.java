package com.adiaz.forms;

import java.io.Serializable;

/**
 * Created by toni on 25/07/2017.
 */
public class TeamFilterForm implements Serializable {
	private Long idCategory;
	private Long idSport;
	private Long idTown;

	public TeamFilterForm(Long idTown, Long idSport, Long idCategory) {
		this.idTown = idTown;
		this.idSport = idSport;
		this.idCategory = idCategory;
	}

	public TeamFilterForm() {

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

	public Long getIdSport() {
		return idSport;
	}

	public void setIdSport(Long idSport) {
		this.idSport = idSport;
	}
}
