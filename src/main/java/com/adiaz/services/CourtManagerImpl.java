package com.adiaz.services;

import java.util.ArrayList;
import java.util.List;

import com.adiaz.daos.MatchesDAO;
import com.adiaz.daos.CenterDAO;
import com.adiaz.daos.SportsDAO;
import com.adiaz.entities.Center;
import com.adiaz.entities.Sport;
import com.adiaz.entities.Court;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CourtDAO;
import com.adiaz.forms.CourtForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("courtManager")
public class CourtManagerImpl implements CourtManager {

	@Autowired
	CourtDAO courtDAO;
	@Autowired
	CenterDAO centerDAO;
	@Autowired
	SportsDAO sportsDAO;
	@Autowired
	MatchesDAO matchesDAO;

	@Override
	public Ref<Court> addSportCourt(Court court) throws Exception {
		return courtDAO.createReturnRef(court);
	}

	@Override
	public Ref<Court> addSportCourt(CourtForm courtForm) throws Exception {
		Court court = courtForm.formToEntity();
		return courtDAO.createReturnRef(court);
	}

	@Override
	public boolean updateSportCourt(Court court) throws Exception {
		return courtDAO.update(court);
	}

	@Override
	public void updateSportCourt(CourtForm courtForm) throws Exception {
		Court court = courtForm.formToEntity();
		courtDAO.update(court);
	}

	@Override
	public boolean removeSportCourt(Long idCourt) throws Exception {
		Court court = courtDAO.findById(idCourt);
		return courtDAO.remove(court);
	}

	@Override
	public void removeAll() throws Exception {
		List<Court> courts = courtDAO.findAll();
		for (Court court : courts) {
			courtDAO.remove(court);
		}
	}

	@Override
	public List<Court> querySportCourt() {
		return courtDAO.findAll();
	}

	@Override
	public Court querySportCourt(Long idCourt) {
		return courtDAO.findById(idCourt);
	}

	@Override
	public List<Court> querySportCourts(Long idCenter) {
		return courtDAO.findBySportCenter(idCenter);
	}


	@Override
	public boolean isElegibleForDelete(Long idCourt) {
		return matchesDAO.findByCourt(idCourt).isEmpty();
	}

	@Override
	public List<Court> querySportCourtsByTownAndSport(Long idTown, Long idSport) {
		Sport sport = sportsDAO.findById(idSport);
		List<Court> courts = new ArrayList<>();
		/*first find centers of this town. */
		List<Center> centers = centerDAO.findByTown(idTown);
		/*second select courts in which it is possible to play the sport. */
		for (Center center : centers) {
			List<Court> c = courtDAO.findBySportCenter(center.getId());
			for (Court court : c) {
				if (court.getSportsDeref().contains(sport)) {
					courts.add(court);
				}
			}
		}
		return courts;
	}
}
