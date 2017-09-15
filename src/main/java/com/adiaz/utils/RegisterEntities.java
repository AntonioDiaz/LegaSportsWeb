package com.adiaz.utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.adiaz.entities.*;
import com.adiaz.forms.*;
import com.adiaz.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.adiaz.entities.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/init")
public class RegisterEntities {

	private static final Logger logger = Logger.getLogger(RegisterEntities.class.getName());
	public static final String LIGA_REGULAR = "Liga Regular";

	@Autowired SportsManager sportsManager;
	@Autowired UsersManager usersManager;
	@Autowired SportCenterManager sportCenterManager;
	@Autowired TownManager townManager;
	@Autowired ClubManager clubManager;

	public void init() throws Exception {
		registerEntities();

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

		Town townLeganes = townManager.queryByName(MuniSportsConstants.TOWN_LEGANES).get(0);
		logger.debug("crea el municipio de leganes");
		Ref<Town> refLega = Ref.create(townLeganes);

		Key<SportCenter> sportsCentersKey = createSportsCenters(refLega);
		logger.debug("crea el centro deportivo");
		Ref<SportCenter> refCenter = Ref.create(sportsCentersKey);
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

	private void creteSportCourt(Ref<SportCenter> refCenter) {
		SportCenterCourt court = new SportCenterCourt();
		court.setName("Pista A");
		court.setSportCenterRef(refCenter);
		for (Sport sport : sportsManager.querySports()) {
			court.getSports().add(Ref.create(sport));
		}
		Key<SportCenterCourt> courtKey = ofy().save().entity(court).now();
	}

	private Key<SportCenter> createSportsCenters(Ref<Town> refTown) throws Exception {
		SportCenter sportsCenter = new SportCenter();
		sportsCenter.setName("Pabellon Europa");
		sportsCenter.setAddress("Av. de Alemania, 2, 28916 Leganés, Madrid");
		sportsCenter.setTownRef(refTown);
		return sportCenterManager.addSportCenter(sportsCenter);
	}

	private void createTowns() throws Exception {
		TownForm townForm = new TownForm();
		townForm.setName(MuniSportsConstants.TOWN_LEGANES);
		townForm.setActive(true);
		townManager.add(townForm);

		townForm = new TownForm();
		townForm.setName(MuniSportsConstants.TOWN_FUENLABRADA);
		townForm.setActive(true);
		townManager.add(townForm);
	}

	private void createCategories() {
		String[] categoriesNames = MuniSportsConstants.CATEGORIES_NAMES;
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
		for (String[] sportsName : MuniSportsConstants.SPORTS_NAMES) {
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
		ObjectifyService.register(SportCenter.class);
		ObjectifyService.register(SportCenterCourt.class);
		ObjectifyService.register(Town.class);
		ObjectifyService.register(Club.class);
		ObjectifyService.register(Team.class);
		ObjectifyService.register(Issue.class);
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
