package com.adiaz.forms;

import lombok.Data;

/**
 * Created by toni on 25/07/2017.
 */
@Data
public class TeamForm {
	private Long id;
	private String name;
	private Long idCategory;
	private Long idTown;
	private Long idClub;
	private Long idSport;
}
