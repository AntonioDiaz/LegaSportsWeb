package com.adiaz.utils;

import com.adiaz.entities.*;
import com.adiaz.forms.ClubForm;
import com.adiaz.forms.TownForm;
import com.adiaz.services.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Controller
@RequestMapping("/init")
public class RegisterEntities {

	private static final Logger logger = Logger.getLogger(RegisterEntities.class.getName());
	public static final String LIGA_REGULAR = "Liga Regular";
	public static final String LEGANES = "Leganés";
	public static final String FUENLABRADA = "Fuenlabrada";

	@Autowired SportsManager sportsManager;
	@Autowired UsersManager usersManager;
	@Autowired CenterManager centerManager;
	@Autowired TownManager townManager;
	@Autowired ClubManager clubManager;
	@Autowired CourtManager courtManager;
	@Autowired MatchesManager matchesManager;

	public void init() throws Exception {
		registerEntities();
	}

	public void initCenters() throws Exception {
		registerEntities();
		courtManager.removeAll();
		centerManager.removeAll();

		logger.debug("create centers and courts");
		List<Ref<Sport>> sportRefsList = new ArrayList<>();
		for (Sport sport : sportsManager.querySports()) {
			sportRefsList.add(Ref.create(sport));
		}

		Town fuenlabrada = townManager.queryByName(FUENLABRADA).get(0);
		Center fernandoMartin = new Center();
		fernandoMartin.setTownRef(Ref.create(fuenlabrada));
		fernandoMartin.setName("Fernando Martin");
		fernandoMartin.setAddress("Calle de Grecia, 2, 28943 Fuenlabrada, Madrid");
		Key<Center> centerFuenlaKey = centerManager.addCenter(fernandoMartin);
		Court courtFuenla = new Court();
		courtFuenla.setName("Pista 01");
		courtFuenla.setSports(sportRefsList);
		courtFuenla.setCenterRef(Ref.create(centerFuenlaKey));
		Ref<Court> courtFuenlaRef = courtManager.addSportCourt(courtFuenla);



		Town leganes = townManager.queryByName(LEGANES).get(0);
		Center pabellonEuropa = new Center();
		pabellonEuropa.setTownRef(Ref.create(leganes));
		pabellonEuropa.setName("Pabellón Europa");
		pabellonEuropa.setAddress("Av. de Alemania, 2, 28916 Leganés, Madrid");
		Key<Center> centerLegaKey = centerManager.addCenter(pabellonEuropa);
		Court courtLega = new Court();
		courtLega.setName("Pista 01");
		courtLega.setSports(sportRefsList);
		courtLega.setCenterRef(Ref.create(centerLegaKey));
		Ref<Court> courtLegaRef = courtManager.addSportCourt(courtLega);


		for (Match match : matchesManager.queryMatches()) {
			System.out.println(match);
			try {
				if (match.getCompetition().getTownEntity().getName().equals(FUENLABRADA)) {
					match.setCourtRef(courtFuenlaRef);
					matchesManager.update(match);
				} else {
					if (match.getCompetition().getTownEntity().getName().equals(LEGANES)) {
						match.setCourtRef(courtLegaRef);
						matchesManager.update(match);
					}
				}
			} catch (Exception e) {
				logger.error("match " + match.getId(), e);
			}
		}
	}

	public void initLarge() throws Exception {
		registerEntities();

		/* clean DB. */
		/*
		logger.debug("DB clean");
		List<Key<Object>> entities = ofy().load().keys().list();
		try {
			ofy().delete().keys(entities).now();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ofy().clear();
		}
		*/
		logger.debug("finished clear");
		createTowns();
		createSports();
		createCategories();

		Town townLeganes = townManager.queryByName(LocalSportsConstants.TOWN_LEGANES).get(0);
		logger.debug("crea el municipio de leganes");
		Ref<Town> refLega = Ref.create(townLeganes);

		Key<Center> sportsCentersKey = createSportsCenters(refLega);
		logger.debug("crea el centro deportivo");
		Ref<Center> refCenter = Ref.create(sportsCentersKey);
		creteSportCourt(refCenter);
		logger.debug("crea la pista");
		createUsers(refLega);
		createClub(refLega);
		logger.debug("finished init...");
	}



	private void createClub(Ref<Town> refTown) {
		try {
			ClubForm clubForm = new ClubForm();
			clubForm.setName("CD LEGANES");
			clubForm.setIdTown(refTown.get().getId());
			clubManager.add(clubForm);
		} catch (Exception e) {
			logger.error("error creating club", e);
		}
	}

	private void createUsers(Ref<Town> refTown) {
		try {
		/* load users */
			String name = "antonio.diaz";
			String password = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
			usersManager.addUser(initUser(name, password, true));

			name = "user.lega";
			password ="8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
			usersManager.addUser(initUser(name, password, false), refTown.get().getId());
		} catch (Exception e) {
			logger.error("Error createing users ", e);
		}
	}

	private void creteSportCourt(Ref<Center> refCenter) {
		Court court = new Court();
		court.setName("Pista A");
		court.setCenterRef(refCenter);
		for (Sport sport : sportsManager.querySports()) {
			court.getSports().add(Ref.create(sport));
		}
		Key<Court> courtKey = ofy().save().entity(court).now();
	}

	private Key<Center> createSportsCenters(Ref<Town> refTown) throws Exception {
		Center sportsCenter = new Center();
		sportsCenter.setName("Pabellon Europa");
		sportsCenter.setAddress("Av. de Alemania, 2, 28916 Leganés, Madrid");
		sportsCenter.setTownRef(refTown);
		return centerManager.addCenter(sportsCenter);
	}

	private void createTowns() throws Exception {
		TownForm townForm = new TownForm();
		townForm.setName(LocalSportsConstants.TOWN_LEGANES);
		townForm.setActive(true);
		townManager.add(townForm);

		townForm = new TownForm();
		townForm.setName(LocalSportsConstants.TOWN_FUENLABRADA);
		townForm.setActive(true);
		townManager.add(townForm);
	}

	private void createCategories() {
		String[] categoriesNames = LocalSportsConstants.CATEGORIES_NAMES;
		int order = 0;
		for (String name : categoriesNames) {
			Category category = new Category();
			category.setName(name);
			category.setOrder(order++);
			ofy().save().entity(category).now();
		}
	}

	private void createSports() {
	/* load sports */
		for (String[] sportsName : LocalSportsConstants.SPORTS_NAMES) {
			String sportName = sportsName[0];
			String sportTag = sportsName[1];
			ofy().save().entity(new Sport(sportName, sportTag)).now();
		}
	}

	private void registerEntities() {
		ObjectifyService.register(Sport.class);
		ObjectifyService.register(Category.class);
		ObjectifyService.register(Competition.class);
		ObjectifyService.register(Match.class);
		ObjectifyService.register(ClassificationEntry.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Center.class);
		ObjectifyService.register(Court.class);
		ObjectifyService.register(Town.class);
		ObjectifyService.register(Club.class);
		ObjectifyService.register(Team.class);
		ObjectifyService.register(Issue.class);
		ObjectifyService.register(Sanction.class);
	}

	private User initUser(String name, String password, boolean isAdmin) {
		User user = new User();
		user.setUsername(name);
		user.setPassword(password);
		user.setAdmin(isAdmin);
		user.setBannedUser(false);
		user.setEnabled(true);
		user.setAccountNonExpired(true);
		return user;
	}
}
