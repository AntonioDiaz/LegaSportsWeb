package com.adiaz.forms;

/**
 * Created by toni on 21/07/2017.
 */
public class MatchForm {
	private Long id;
	private Integer week;
	private Long courtId;
	private String courtName;
	private String dateStr;
	private Long teamLocalId;
	private Long teamVisitorId;
	private String teamLocalName;
	private String teamVisitorName;
	private int scoreLocal;
	private int scoreVisitor;
	private boolean updatedDate;
	private boolean updatedScore;
	private boolean updatedCourt;
	private boolean updatedTeamLocal;
	private boolean updatedTeamVisitor;
	private Short state;

	public boolean checkIfChangesToPublish() {
		return updatedDate || updatedCourt || updatedScore || updatedTeamLocal || updatedTeamVisitor;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCourtId() {
		return courtId;
	}

	public void setCourtId(Long courtId) {
		this.courtId = courtId;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Long getTeamLocalId() {
		return teamLocalId;
	}

	public void setTeamLocalId(Long teamLocalId) {
		this.teamLocalId = teamLocalId;
	}

	public Long getTeamVisitorId() {
		return teamVisitorId;
	}

	public void setTeamVisitorId(Long teamVisitorId) {
		this.teamVisitorId = teamVisitorId;
	}

	public String getTeamLocalName() {
		return teamLocalName;
	}

	public void setTeamLocalName(String teamLocalName) {
		this.teamLocalName = teamLocalName;
	}

	public String getTeamVisitorName() {
		return teamVisitorName;
	}

	public void setTeamVisitorName(String teamVisitorName) {
		this.teamVisitorName = teamVisitorName;
	}

	public int getScoreLocal() {
		return scoreLocal;
	}

	public void setScoreLocal(int scoreLocal) {
		this.scoreLocal = scoreLocal;
	}

	public int getScoreVisitor() {
		return scoreVisitor;
	}

	public void setScoreVisitor(int scoreVisitor) {
		this.scoreVisitor = scoreVisitor;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public boolean isUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(boolean updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isUpdatedScore() {
		return updatedScore;
	}

	public void setUpdatedScore(boolean updatedScore) {
		this.updatedScore = updatedScore;
	}

	public boolean isUpdatedCourt() {
		return updatedCourt;
	}

	public void setUpdatedCourt(boolean updatedCourt) {
		this.updatedCourt = updatedCourt;
	}

	public boolean isUpdatedTeamLocal() {
		return updatedTeamLocal;
	}

	public void setUpdatedTeamLocal(boolean updatedTeamLocal) {
		this.updatedTeamLocal = updatedTeamLocal;
	}

	public boolean isUpdatedTeamVisitor() {
		return updatedTeamVisitor;
	}

	public void setUpdatedTeamVisitor(boolean updatedTeamVisitor) {
		this.updatedTeamVisitor = updatedTeamVisitor;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}
}
