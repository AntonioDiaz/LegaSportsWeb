package com.adiaz.forms;

import lombok.Data;

/**
 * Created by toni on 21/07/2017.
 */
@Data
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
}
