package com.adiaz.forms;

import lombok.Data;

/**
 * Created by toni on 14/07/2017.
 */
@Data
public class TownForm {
	private Long id;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String contactPerson;
	private boolean isActive;
	private Long[] sports;
}
