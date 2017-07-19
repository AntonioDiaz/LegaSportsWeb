package com.adiaz.forms;

import java.io.Serializable;

/**
 * Created by toni on 19/07/2017.
 */
public class CompetitionsFilterForm implements Serializable {

	private Long idSport;
	private Long idCategory;
	private Long idTown;

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

	public Long getIdTown() {
		return idTown;
	}

	public void setIdTown(Long idTown) {
		this.idTown = idTown;
	}
}
