package com.adiaz.forms;

import com.adiaz.entities.Court;
import com.adiaz.entities.Sport;
import com.adiaz.entities.Center;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

@Data
public class CourtForm {

	private Long idCourt;
	private Long idCenter;
	private String nameCenter;
	private String name;
	private Long[] courtsSports;

	public CourtForm() {
		super();
	}

	public CourtForm(Court court) {
		super();
		this.setIdCourt(court.getId());
		this.setIdCenter(court.getCenterRef().get().getId());
		this.setNameCenter(court.getCenterRef().get().getName());
		this.setName(court.getName());
		Long [] sportsIds = new Long[court.getSportsDeref().size()];
		for (int i = 0; i < sportsIds.length; i++) {
			sportsIds[i] = court.getSportsDeref().get(i).getId();
		}
		this.setCourtsSports(sportsIds);
	}

	public Court getCourt() {
		Court court = new Court();
		court.setId(this.getIdCourt());
		court.setName(this.getName());
		for (Long idSport : this.getCourtsSports()) {
			Key<Sport> newSport = Key.create(Sport.class, idSport);
			court.getSports().add(Ref.create(newSport));
		}		
		Key<Center> refCenter = Key.create(Center.class, this.getIdCenter());
		court.setCenterRef(Ref.create(refCenter));
		return court;
	}
}
