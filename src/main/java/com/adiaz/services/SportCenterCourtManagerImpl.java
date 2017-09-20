package com.adiaz.services;

import java.util.ArrayList;
import java.util.List;

import com.adiaz.daos.MatchesDAO;
import com.adiaz.daos.SportCenterDAO;
import com.adiaz.daos.SportsDAO;
import com.adiaz.entities.Sport;
import com.adiaz.entities.SportCenterCourt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.SportCenterCourtDAO;
import com.adiaz.entities.SportCenter;
import com.adiaz.forms.SportsCourtForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("sportCourtManager")
public class SportCenterCourtManagerImpl implements SportCenterCourtManager {

	@Autowired
	SportCenterCourtDAO sportCenterCourtDAO;
	@Autowired
	SportCenterDAO sportCenterDAO;
	@Autowired
	SportsDAO sportsDAO;
	@Autowired
	MatchesDAO matchesDAO;

	@Override
	public Ref<SportCenterCourt> addSportCourt(SportCenterCourt sportCenterCourt) throws Exception {
		return sportCenterCourtDAO.createReturnRef(sportCenterCourt);
	}

	@Override
	public boolean updateSportCourt(SportCenterCourt sportCenterCourt) throws Exception {
		return sportCenterCourtDAO.update(sportCenterCourt);
	}

	@Override
	public boolean removeSportCourt(Long idCourt) throws Exception {
		SportCenterCourt sportCenterCourt = sportCenterCourtDAO.findBySportCenter(idCourt);
		return sportCenterCourtDAO.remove(sportCenterCourt);
	}

	@Override
	public List<SportCenterCourt> querySportCourt() {
		return sportCenterCourtDAO.findAll();
	}

	@Override
	public void removeAll() throws Exception {
		List<SportCenterCourt> courts = sportCenterCourtDAO.findAll();
		for (SportCenterCourt court : courts) {
			sportCenterCourtDAO.remove(court);
		}
	}

	@Override
	public SportCenterCourt querySportCourt(Long idCourt) {
		return sportCenterCourtDAO.findBySportCenter(idCourt);
	}

	@Override
	public Ref<SportCenterCourt> addSportCourt(SportsCourtForm sportsCourtForm) throws Exception {
		SportCenterCourt sportCenterCourt = sportsCourtForm.getCourt();
		return Ref.create(sportCenterCourtDAO.create(sportCenterCourt));
	}

	@Override
	public List<SportCenterCourt> querySportCourts(Long idSportCenter) {
		Key<SportCenter> keyCenter = Key.create(SportCenter.class, idSportCenter);
		Ref<SportCenter> refCenter = Ref.create(keyCenter);
		return sportCenterCourtDAO.findBySportCenter(refCenter);
	}

	@Override
	public void updateSportCourt(SportsCourtForm sportsCourtForm) throws Exception {
		SportCenterCourt sportCenterCourt = sportsCourtForm.getCourt();
		sportCenterCourtDAO.update(sportCenterCourt);
	}

	@Override
	public boolean isElegibleForDelete(Long idSportCenterCourt) {
		return matchesDAO.findByCourt(idSportCenterCourt).isEmpty();
	}

	@Override
	public List<SportCenterCourt> querySportCourtsByTownAndSport(Long idTown, Long idSport) {
		Sport sport = sportsDAO.findById(idSport);
		List<SportCenterCourt> courts = new ArrayList<>();
		/*first find sportCenters of this town. */
		List<SportCenter> sportCenters = sportCenterDAO.findByTown(idTown);
		/*second select courts in which it is possible to play the sport. */
		for (SportCenter sportCenter : sportCenters) {
			List<SportCenterCourt> c = sportCenterCourtDAO.findBySportCenter(Ref.create(sportCenter));
			for (SportCenterCourt sportCenterCourt : c) {
				if (sportCenterCourt.getSportsDeref().contains(sport)) {
					courts.add(sportCenterCourt);
				}
			}
		}
		return courts;
	}
}
