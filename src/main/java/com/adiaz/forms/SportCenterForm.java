package com.adiaz.forms;

/**
 * Created by toni on 17/07/2017.
 */
public class SportCenterForm {
	private Long id;
	private boolean isAdmin;
	private String name;
	private String address;
	private Long idTown;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getIdTown() {
		return idTown;
	}

	public void setIdTown(Long idTown) {
		this.idTown = idTown;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
}
