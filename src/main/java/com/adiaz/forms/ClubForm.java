package com.adiaz.forms;

import lombok.Data;

/**
 * Created by toni on 25/07/2017.
 */
@Data
public class ClubForm {
	private Long id;
	private String name;
	private String contactPerson;
	private String contactEmail;
	private String contactAddress;
	private String contactPhone;
	private Long idTown;

}
