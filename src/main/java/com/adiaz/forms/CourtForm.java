package com.adiaz.forms;

import com.adiaz.entities.Court;
import com.adiaz.entities.Sport;
import com.adiaz.entities.Center;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import lombok.Data;

@Data
public class CourtForm implements GenericForm<Court>{

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
		if (court.getCenterRef()!=null) {
			this.setIdCenter(court.getCenterRef().get().getId());
			this.setNameCenter(court.getCenterRef().get().getName());
		}
		this.setName(court.getName());
		if (court.getSportsDeref()!=null) {
			Long [] sportsIds = new Long[court.getSportsDeref().size()];
			for (int i = 0; i < sportsIds.length; i++) {
				sportsIds[i] = court.getSportsDeref().get(i).getId();
			}
			this.setCourtsSports(sportsIds);
		}
	}

	@Override
	public Court formToEntity() {
		return formToEntity(new Court());
	}

	@Override
	public Court formToEntity(Court court) {
		court.setId(idCourt);
		court.setName(name);
		for (Long idSport : courtsSports) {
			Key<Sport> newSport = Key.create(Sport.class, idSport);
			court.getSports().add(Ref.create(newSport));
		}
		if (idCenter!=null) {
			Key<Center> refCenter = Key.create(Center.class, idCenter);
			court.setCenterRef(Ref.create(refCenter));
		}
		return court;

	}
}
