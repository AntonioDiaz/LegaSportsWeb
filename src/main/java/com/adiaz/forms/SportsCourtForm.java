package com.adiaz.forms;

import com.adiaz.entities.Sport;
import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCourt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

public class SportsCourtForm {

	private Long idCourt;
	private Long idCenter;
	private String nameCenter;
	private String name;
	private Long[] courtsSports;
	
	public SportsCourtForm(SportCourt court) {
		super();
		this.setIdCourt(court.getId());
		this.setIdCenter(court.getCenter().get().getId());
		this.setNameCenter(court.getCenter().get().getName());
		this.setName(court.getName());
		Long [] sportsIds = new Long[court.getSportsDeref().size()];
		for (int i = 0; i < sportsIds.length; i++) {
			sportsIds[i] = court.getSportsDeref().get(i).getId();
		}
		this.setCourtsSports(sportsIds);
	}
	public SportsCourtForm() {
		super();
	}
	
	public Long getIdCenter() {
		return idCenter;
	}
	public void setIdCenter(Long idCenter) {
		this.idCenter = idCenter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long[] getCourtsSports() {
		return courtsSports;
	}
	public void setCourtsSports(Long[] courtsSports) {
		this.courtsSports = courtsSports;
	}
	public Long getIdCourt() {
		return idCourt;
	}
	public void setIdCourt(Long idCourt) {
		this.idCourt = idCourt;
	}
	public String getNameCenter() {
		return nameCenter;
	}
	public void setNameCenter(String nameCenter) {
		this.nameCenter = nameCenter;
	}
	public SportCourt getCourt() {
		SportCourt sportCourt = new SportCourt();
		sportCourt.setId(this.getIdCourt());
		sportCourt.setName(this.getName());	
		for (Long idSport : this.getCourtsSports()) {
			Key<Sport> newSport = Key.create(Sport.class, idSport);
			sportCourt.getSports().add(Ref.create(newSport));
		}		
		Key<SportCenter> refCenter = Key.create(SportCenter.class, this.getIdCenter());
		sportCourt.setCenter(Ref.create(refCenter));
		return sportCourt;
	}
}
