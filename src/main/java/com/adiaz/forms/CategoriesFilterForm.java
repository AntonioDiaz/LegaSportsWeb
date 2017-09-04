package com.adiaz.forms;

import java.io.Serializable;

/**
 * Created by toni on 04/09/2017.
 */
public class CategoriesFilterForm implements Serializable {
	Long idTown;

	public Long getIdTown() {
		return idTown;
	}

	public void setIdTown(Long idTown) {
		this.idTown = idTown;
	}
}
