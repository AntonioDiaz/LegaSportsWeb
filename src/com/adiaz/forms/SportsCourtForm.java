package com.adiaz.forms;

public class SportsCourtForm {

	private Long idCenter;
	private String name;
	private Long[] courtsSports;
	
	public Long getIdCenter() {
		return idCenter;
	}
	public void setIdCenter(Long idCenter) {
		this.idCenter = idCenter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long[] getCourtsSports() {
		return courtsSports;
	}
	public void setCourtsSports(Long[] courtsSports) {
		this.courtsSports = courtsSports;
	}
}
