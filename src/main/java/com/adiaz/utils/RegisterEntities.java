package com.adiaz.utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;

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
		for (String sportName : ConstantsLegaSport.SPORTS_NAMES) {
			Key<Sport> key = ofy().save().entity(new Sport(sportName)).now();
			if (ConstantsLegaSport.BASKET.equals(sportName)) {
				keySportBasket = key;
			}
		}
		
		/* load categories */
		String[] categoriesNames = ConstantsLegaSport.CATEGORIES_NAMES;
		int order = 0;
		for (String name : categoriesNames) {
			Category category = new Category();
			category.setName(name);
			category.setOrder(order++);
			ofy().save().entity(category).now();
		}
		Category category = categoriesManager.queryCategoriesByName("Cadete");
		keyCadete = Key.create(category);
		keyJuvenil = Key.create(categoriesManager.queryCategoriesByName("Juvenil"));

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


		/* load competitions */
		Competition competition = new Competition();
		competition.setName("liga division honor");
		competition.setCategoryRef(Ref.create(keyJuvenil));
		competition.setSportRef(Ref.create(keySportBasket));
		competition.setTownRef(Ref.create(keyLega));
		try {
			competitionsManager.add(competition);
			logger.debug("insert competition");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Match> matchesList = UtilsLegaSport.parseCalendar(competition, Ref.create(courtKey));
		try {
			matchesManager.addMatchListAndPublish(matchesList);
		} catch (Exception e) {
			logger.error(e);
		}
		
		try {
			List<ClassificationEntry> classificationList = UtilsLegaSport.parseClassification(competition.getId());
			classificationManager.add(classificationList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		competition = new Competition();
		competition.setName("Liga division honor");
		competition.setCategoryRef(Ref.create(keyCadete));
		competition.setSportRef(Ref.create(keySportBasket));
		competition.setTownRef(Ref.create(keyLega));
		competitionsManager.add(competition);


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


		createTeams(idCdLeganes, townIdLega, keyCadete.getId(), keySportBasket.getId());
		logger.debug("finished init...");
	}

	private void createTeams(Long idClub, Long idTown, long idCategory, long idSport) throws Exception {
		String[] teamsNames = new String[]{"CD LEGANES A", "Legamar A", "Concepcion Arenal", "Lope de Vega"};
		for (int i = 0; i < teamsNames.length; i++) {
			TeamForm teamForm = new TeamForm();
			teamForm.setName(teamsNames[i]);
			teamForm.setIdClub(idClub);
			teamForm.setIdTown(idTown);
			teamForm.setIdCategory(idCategory);
			teamForm.setIdSport(idSport);
			teamManager.add(teamForm);
		}
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
