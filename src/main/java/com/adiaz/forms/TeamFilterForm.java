package com.adiaz.forms;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by toni on 25/07/2017.
 */
@Data
public class TeamFilterForm implements Serializable {
	private Long idCategory;
	private Long idSport;
	private Long idTown;

	public TeamFilterForm(Long idTown, Long idSport, Long idCategory) {
		this.idTown = idTown;
		this.idSport = idSport;
		this.idCategory = idCategory;
	}

	public TeamFilterForm() { }
}
