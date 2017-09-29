package com.adiaz.forms;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sanction;
import com.adiaz.entities.Team;
import com.adiaz.forms.utils.GenericFormUtils;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

/**
 * Created by toni on 28/09/2017.
 */
@Data
public class SanctionForm implements GenericForm<Sanction> {

	private Long idTeam;
	private Long idCompetition;
	private Integer points;
	private String description;

	public SanctionForm() {	}

	public SanctionForm(Sanction sanction) {
		points = sanction.getPoints();
		description = sanction.getDescription();
		if (sanction.getCompetition()!=null) {
			idCompetition = sanction.getCompetition().getId();
		}
		if (sanction.getTeam()!=null) {
			idTeam = sanction.getTeam().getId();
		}
	}

	@Override
	public Sanction entity(Sanction sanction) {
		sanction.setPoints(points);
		sanction.setDescription(description);
		if (idTeam!=null) {
			Key<Team> key = Key.create(Team.class, idTeam);
			sanction.setTeamRef(Ref.create(key));
		}
		if (idCompetition!=null) {
			Key<Competition> key = Key.create(Competition.class, idCompetition);
			sanction.setCompetitionRef(Ref.create(key));
		}
		return sanction;
	}

	@Override
	public Sanction entity() {
		return entity(new Sanction());
	}
}
