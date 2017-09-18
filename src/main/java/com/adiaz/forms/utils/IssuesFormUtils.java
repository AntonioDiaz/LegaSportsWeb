package com.adiaz.forms.utils;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Issue;
import com.adiaz.entities.Match;
import com.adiaz.entities.Town;
import com.adiaz.forms.IssuesForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

/**
 * Created by toni on 14/09/2017.
 */
@Repository
public class IssuesFormUtils implements GenericFormUtils<IssuesForm, Issue> {

	@Override
	public Issue formToEntity(IssuesForm form) {
		Issue issue = new Issue();
		formToEntity(issue, form);
		return issue;
	}

	@Override
	public void formToEntity(Issue entity, IssuesForm form) {
		entity.setClientId(form.getClientId());
		entity.setDateSent(form.getDateSent());
		entity.setDescription(form.getDescription());
		if (form.getCompetitionId()!=null) {
			Key<Competition> key = Key.create(Competition.class, form.getCompetitionId());
			entity.setCompetitionRef(Ref.create(key));
		}
		if (form.getMatchId()!=null) {
			Key<Match> matchKey = Key.create(Match.class, form.getMatchId());
			entity.setMatchRef(Ref.create(matchKey));
		}
		if (form.getTownId()!=null) {
			Key<Town> townKey = Key.create(Town.class, form.getTownId());
			entity.setTownRef(Ref.create(townKey));
		}
	}

	@Override
	public IssuesForm entityToForm(Issue entity) {
		IssuesForm issuesForm = new IssuesForm();
		issuesForm.setClientId(entity.getClientId());
		issuesForm.setDateSent(entity.getDateSent());
		issuesForm.setDescription(entity.getDescription());
		if (entity.getCompetition()!=null) {
			issuesForm.setCompetitionId(entity.getCompetition().getId());
		}
		if (entity.getMatch()!=null) {
			issuesForm.setMatchId(entity.getMatch().getId());
		}
		if (entity.getTown()!=null) {
			issuesForm.setTownId(entity.getTown().getId());
		}
		return issuesForm;
	}
}
