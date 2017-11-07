package com.adiaz.forms;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Issue;
import com.adiaz.entities.Match;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by toni on 14/09/2017.
 */
@Data
public class IssuesForm implements Serializable, GenericForm<Issue> {

	private String clientId;
	private Long competitionId;
	private Long matchId;
	private Long townId;
	private String description;
	private Date dateSent;

	public IssuesForm() {
	}

	public IssuesForm(Issue issue) {
		clientId = issue.getClientId();
		dateSent = issue.getDateSent();
		description = issue.getDescription();

		if (issue.getCompetition()!=null) {
			competitionId = issue.getCompetition().getId();
		}
		if (issue.getMatch()!=null) {
			matchId = issue.getMatch().getId();
		}
		if (issue.getTown()!=null) {
			townId = issue.getTown().getId();
		}
	}

	@Override
	public Issue formToEntity() {
		return formToEntity(new Issue());
	}

	@Override
	public Issue formToEntity(Issue entity) {
		entity.setClientId(clientId);
		entity.setDateSent(dateSent);
		entity.setDescription(description);
		if (competitionId!=null) {
			Key<Competition> key = Key.create(Competition.class, competitionId);
			entity.setCompetitionRef(Ref.create(key));
		}
		if (matchId!=null) {
			Key<Match> matchKey = Key.create(Match.class, matchId);
			entity.setMatchRef(Ref.create(matchKey));
		}
		if (townId!=null) {
			Key<Town> townKey = Key.create(Town.class, townId);
			entity.setTownRef(Ref.create(townKey));
		}
		return entity;
	}
}
