package com.adiaz.forms;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by toni on 19/07/2017.
 */
@Data
public class CompetitionsFilterForm implements Serializable {

	private Long idSport;
	private Long idCategory;
	private Long idTown;

}
