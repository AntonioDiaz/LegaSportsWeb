package com.adiaz.forms;

import lombok.Data;

@Data
public class CompetitionsForm {

	private Long id;
	private String name;
	private Long idSport;
	private Long idCategory;
	private Long idTown;
	private Long[] teams;
}
