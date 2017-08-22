package com.adiaz.utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.*;

import com.adiaz.entities.*;
import com.adiaz.forms.ClubForm;
import com.adiaz.forms.TeamForm;
import com.adiaz.forms.TownForm;
import com.adiaz.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.adiaz.entities.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

public class RegisterEntities {

	private static final Logger logger = Logger.getLogger(RegisterEntities.class.getName());
	public static final String LIGA_DIVISION_HONOR = "Liga division honor";
	public static final String COPA_DE_PRIMAVERA = "Copa de Primavera";

	@Autowired SportsManager sportsManager;
	@Autowired CategoriesManager categoriesManager;
	@Autowired CompetitionsManager competitionsManager;
	@Autowired UsersManager usersManager;
	@Autowired SportCenterManager sportCenterManager;
	@Autowired SportCenterCourtManager sportCenterCourtManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;
	@Autowired TownManager townManager;
	@Autowired ClubManager clubManager;
	@Autowired TeamManager teamManager;

	public void init() throws Exception {
		registerEntities();
	}
	public void initLong() throws Exception {

		registerEntities();

		/* clean DB. */
		logger.debug("DB clean");
		sportsManager.removeAll();
		categoriesManager.removeAll();
		matchesManager.removeAll();
		classificationManager.removeAll();
		competitionsManager.removeAll();
		usersManager.removeAll();
		sportCenterCourtManager.removeAll();
		sportCenterManager.removeAll();
		townManager.removeAll();
		clubManager.removaAll();
		teamManager.removeAll();


		TownForm townForm = new TownForm();
		townForm.setName("Leganés");
		townForm.setActive(true);
		Long townIdLega = townManager.add(townForm);
		Key<Town> keyLega = Key.create(Town.class, townIdLega);

		townForm = new TownForm();
		townForm.setName("Fuenlabrada");
		townForm.setActive(true);
		Long townIdFuenla = townManager.add(townForm);
		Key<Town> keyFuenla = Key.create(Town.class, townIdFuenla);

		/* load sports */
		 Key<Sport> keySportBasket = null;
		 Key<Category> keyCadete = null;
		 Key<Category> keyJuvenil = null;
		 Key<Category> keyInfantil= null;
		for (String[] sportsName : MuniSportsConstants.SPORTS_NAMES) {
			String sportName = sportsName[0];
			String sportTag = sportsName[1];
			Key<Sport> key = ofy().save().entity(new Sport(sportName, sportTag)).now();
			if (MuniSportsConstants.BASKET.equals(sportName)) {
				keySportBasket = key;
			}
		}
		
		/* load categories */
		String[] categoriesNames = MuniSportsConstants.CATEGORIES_NAMES;
		int order = 0;
		for (String name : categoriesNames) {
			Category category = new Category();
			category.setName(name);
			category.setOrder(order++);
			ofy().save().entity(category).now();
		}
		keyCadete = Key.create(categoriesManager.queryCategoriesByName("Cadete"));
		keyJuvenil = Key.create(categoriesManager.queryCategoriesByName("Juvenil"));
		keyInfantil = Key.create(categoriesManager.queryCategoriesByName("Infantil"));

		SportCenter sportsCenter = new SportCenter();
		sportsCenter.setName("Pabellon Europa");
		sportsCenter.setAddress("Av. de Alemania, 2, 28916 Leganés, Madrid");
		sportsCenter.setTownRef(Ref.create(keyLega));
		Key<SportCenter> centerKey = ofy().save().entity(sportsCenter).now();

		SportCenterCourt court = new SportCenterCourt();
		court.setName("Pista central");
		court.setSportCenterRef(Ref.create(centerKey));
		court.getSports().add(Ref.create(keySportBasket));
		Key<SportCenterCourt> courtKey = ofy().save().entity(court).now();

		/* load users */
		String name = "antonio.diaz";
		String password = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
		usersManager.addUser(initUser(name, password, true));

		name = "user.lega";
		password ="8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
		usersManager.addUser(initUser(name, password, false), townIdLega);

		ClubForm clubForm = new ClubForm();
		clubForm.setName("CD LEGANES");
		clubForm.setIdTown(keyLega.getId());
		Long idCdLeganes = clubManager.add(clubForm);


		Set<String> teamsSet = MuniSportsUtils.parseCalendarGetTeams();
		List<String> teamsList = new ArrayList<>();
		teamsList.addAll(teamsSet);
		Map<String, Ref<Team>> teamsMap = createTeams(teamsList, idCdLeganes, townIdLega, keyJuvenil.getId(), keySportBasket.getId());
		Collection<Ref<Team>> values = teamsMap.values();
		List<Ref<Team>> teamsRefList = new ArrayList<>(values);

		try {
		/* load competitions */
			Long idCompetition = createCompetition(LIGA_DIVISION_HONOR, keyJuvenil, keySportBasket, keyLega, teamsRefList);
			List<Match> matchesList = MuniSportsUtils.parseCalendar(idCompetition, Ref.create(courtKey), teamsMap);
			matchesManager.addMatchListAndPublish(matchesList);
			List<ClassificationEntry> classificationList = MuniSportsUtils.parseClassification(idCompetition);
			classificationManager.add(classificationList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			createCompetition(LIGA_DIVISION_HONOR, keyInfantil, keySportBasket, keyLega, null);
			createCompetition(COPA_DE_PRIMAVERA, keyInfantil, keySportBasket, keyLega, null);
			createCompetition(LIGA_DIVISION_HONOR, keyCadete, keySportBasket, keyLega, null);
			createCompetition(COPA_DE_PRIMAVERA, keyJuvenil, keySportBasket, keyLega, null);
			createCompetition(COPA_DE_PRIMAVERA, keyCadete, keySportBasket, keyLega, null);
		} catch (Exception e) {

		}

		logger.debug("finished init...");
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
	}

	private Long createCompetition(
			String ligaDivisionHonor, Key<Category> keyJuvenil, Key<Sport> keySportBasket, Key<Town> keyLega, List<Ref<Team>> teamsList) throws Exception {
		Competition competition = new Competition();
		competition.setName(ligaDivisionHonor);
		competition.setCategoryRef(Ref.create(keyJuvenil));
		competition.setSportRef(Ref.create(keySportBasket));
		competition.setTownRef(Ref.create(keyLega));
		competition.setTeams(teamsList);
		return competitionsManager.add(competition);
	}

	private Map<String, Ref<Team>> createTeams(List<String> teamsList, Long idClub, Long idTown, long idCategory, long idSport) throws Exception {
		Map<String, Ref<Team>> teamsMap = new HashMap<>();
		for (String teamName : teamsList) {
			TeamForm teamForm = new TeamForm();
			teamForm.setName(teamName);
			teamForm.setIdClub(idClub);
			teamForm.setIdTown(idTown);
			teamForm.setIdCategory(idCategory);
			teamForm.setIdSport(idSport);
			Long id = teamManager.add(teamForm);
			teamsMap.put(teamName, Ref.create(Key.create(Team.class, id)));
		}
		return teamsMap;
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
