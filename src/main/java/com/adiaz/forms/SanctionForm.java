package com.adiaz.forms;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sanction;
import com.adiaz.entities.Team;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

/**
 * Created by toni on 28/09/2017.
 */
@Data
public class SanctionForm {

	private Long idTeam;
	private Long idCompetition;
	private Integer points;
	private String description;

	public SanctionForm() {	}

	public SanctionForm(Long idTeam, Long idCompetition, Integer points, String description) {
		this.idTeam = idTeam;
		this.idCompetition = idCompetition;
		this.points = points;
		this.description = description;
	}

	public Sanction convertToEntity() {
		Sanction sanction = new Sanction();
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

	public static SanctionForm convertToForm(Sanction sanction) {
		SanctionForm sanctionForm = new SanctionForm();
		sanctionForm.setPoints(sanction.getPoints());
		sanctionForm.setDescription(sanction.getDescription());
		if (sanction.getCompetition()!=null) {
			sanctionForm.setIdCompetition(sanction.getCompetition().getId());
		}
		if (sanction.getTeam()!=null) {
			sanctionForm.setIdTeam(sanction.getTeam().getId());
		}
		return sanctionForm;
	}
}
