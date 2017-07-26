package com.adiaz.forms;

import java.io.Serializable;

/**
 * Created by toni on 25/07/2017.
 */
public class TeamFilterForm implements Serializable {
	private Long idCategory;
	private Long idTown;

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
