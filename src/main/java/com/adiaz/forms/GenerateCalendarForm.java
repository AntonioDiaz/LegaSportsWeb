package com.adiaz.forms;

/**
 * Created by toni on 26/07/2017.
 */
public class GenerateCalendarForm {
	private Long idCompetition;
	private Long idCourt;
	private Integer numWeeks;
	private Integer numTeams;
	private Long[] teams;

	public Long getIdCompetition() {
		return idCompetition;
	}

	public void setIdCompetition(Long idCompetition) {
		this.idCompetition = idCompetition;
	}

	public Long getIdCourt() {
		return idCourt;
	}

	public void setIdCourt(Long idCourt) {
		this.idCourt = idCourt;
	}

	public Integer getNumWeeks() {
		return numWeeks;
	}

	public void setNumWeeks(Integer numWeeks) {
		this.numWeeks = numWeeks;
	}

	public Integer getNumTeams() {
		return numTeams;
	}

	public void setNumTeams(Integer numTeams) {
		this.numTeams = numTeams;
	}

	public Long[] getTeams() {
		return teams;
	}

	public void setTeams(Long[] teams) {
		this.teams = teams;
	}
}
