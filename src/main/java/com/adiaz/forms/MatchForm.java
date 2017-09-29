package com.adiaz.forms;

import com.adiaz.entities.Court;
import com.adiaz.entities.Match;
import com.adiaz.entities.Team;
import com.adiaz.utils.LocalSportsUtils;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

import java.util.Objects;

/**
 * Created by toni on 21/07/2017.
 */
@Data
public class MatchForm implements GenericForm<Match>{
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

	public MatchForm() {
	}

	public MatchForm(Match e) {
		id = e.getId();
		week = e.getWeek();
		dateStr = LocalSportsUtils.parseDateToString(e.getDate());
		scoreLocal = e.getScoreLocal();
		scoreVisitor = e.getScoreVisitor();
		if (e.getTeamLocalEntity()!=null) {
			teamLocalId = e.getTeamLocalEntity().getId();
			teamLocalName = e.getTeamLocalEntity().getName();
		}
		if (e.getTeamVisitorEntity()!=null) {
			teamVisitorId = e.getTeamVisitorEntity().getId();
			teamVisitorName = e.getTeamVisitorEntity().getName();
		}
		if (e.getCourt()!=null) {
			courtId = e.getCourt().getId();
			courtName = e.getCourt().getNameWithCenter();
		}
		if (e.getMatchPublished()!=null) {
			Match matchPublished = e.getMatchPublished();
			if (e.getScoreLocal()!=matchPublished.getScoreLocal()
					|| e.getScoreVisitor()!=matchPublished.getScoreVisitor()
					|| e.getState()!=matchPublished.getState()) {
				updatedScore = true;
			}
			if (!Objects.equals(e.getDate(), matchPublished.getDate())) {
				updatedDate = true;
			}
			if (!Objects.equals(e.getCourt(), matchPublished.getCourt())) {
				updatedCourt = true;
			}
			if (!Objects.equals(e.getTeamLocalEntity(), matchPublished.getTeamLocalEntity())) {
				updatedTeamLocal = true;
			}
			if (!Objects.equals(e.getTeamVisitorEntity(), matchPublished.getTeamVisitorEntity())) {
				updatedTeamVisitor = true;
			}
		}
		state = e.getState();
	}


	public boolean checkIfChangesToPublish() {
		return updatedDate || updatedCourt || updatedScore || updatedTeamLocal || updatedTeamVisitor;
	}

	@Override
	public Match formToEntity() {
		return formToEntity(new Match());
	}

	@Override
	public Match formToEntity(Match match) {
		match.setScoreLocal(scoreLocal);
		match.setScoreVisitor(scoreVisitor);
		match.setDate(LocalSportsUtils.parseStringToDate(dateStr));
		match.setCourtRef(null);
		if (courtId!=null) {
			Key<Court> key = Key.create(Court.class, courtId);
			Ref<Court> courtRef = Ref.create(key);
			match.setCourtRef(courtRef);
		}
		match.setTeamLocalRef(null);
		if (teamLocalId!=null) {
			Key<Team> key = Key.create(Team.class, teamLocalId);
			Ref<Team> refLocal = Ref.create(key);
			match.setTeamLocalRef(refLocal);
		}
		match.setTeamVisitorRef(null);
		if (teamVisitorId!=null) {
			Key<Team> key = Key.create(Team.class, teamVisitorId);
			Ref<Team> refVisitor = Ref.create(key);
			match.setTeamVisitorRef(refVisitor);
		}
		match.setState(state);
		return match;
	}
}
