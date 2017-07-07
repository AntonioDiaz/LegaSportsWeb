package com.adiaz.utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.adiaz.entities.CategoriesVO;
import com.adiaz.entities.ClassificationEntryVO;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCourt;
import com.adiaz.entities.SportVO;
import com.adiaz.entities.UsersVO;
import com.adiaz.services.CategoriesManager;
import com.adiaz.services.ClassificationManager;
import com.adiaz.services.CompetitionsManager;
import com.adiaz.services.MatchesManager;
import com.adiaz.services.SportCenterManager;
import com.adiaz.services.SportCourtManager;
import com.adiaz.services.SportsManager;
import com.adiaz.services.UsersManager;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

public class RegisterEntities {

	private static final Logger logger = Logger.getLogger(RegisterEntities.class.getName());
	
	@Autowired SportsManager sportsManager;
	@Autowired CategoriesManager categoriesManager;
	@Autowired CompetitionsManager competitionsManager;
	@Autowired UsersManager usersManager;
	@Autowired SportCenterManager sportsCenterManager;
	@Autowired SportCourtManager sportCourtManager;
	@Autowired MatchesManager matchesManager;
	@Autowired ClassificationManager classificationManager;	
	
	public void init() throws Exception {
		ObjectifyService.register(SportVO.class);
		ObjectifyService.register(CategoriesVO.class);
		ObjectifyService.register(CompetitionsVO.class);
		ObjectifyService.register(MatchesVO.class);
		ObjectifyService.register(ClassificationEntryVO.class);
		ObjectifyService.register(UsersVO.class);
		ObjectifyService.register(SportCenter.class);
		ObjectifyService.register(SportCourt.class);
		/** clean DB. */
		sportsManager.removeAll();
		categoriesManager.removeAll();
		matchesManager.removeAll();
		classificationManager.removeAll();
		competitionsManager.removeAll();
		usersManager.removeAll();
		sportCourtManager.removeAll();
		sportsCenterManager.removeAll();
		logger.debug("DB clean");
		
		/** load sports */
		 Key<SportVO> keySport = null;
		 Key<CategoriesVO> keyCategories = null;
		for (String sportName : ConstantsLegaSport.SPORTS_NAMES) {
			 keySport = ofy().save().entity(new SportVO(sportName)).now();
		}
		
		/** load categories */
		String[] categoriesNames = ConstantsLegaSport.CATEGORIES_NAMES;
		int order = 0;
		for (String name : categoriesNames) {
			CategoriesVO categoriesVO = new CategoriesVO();
			categoriesVO.setName(name);
			categoriesVO.setOrder(order++);
			keyCategories = ofy().save().entity(categoriesVO).now();
		}
		/** load competitions */
		CompetitionsVO competition = new CompetitionsVO();
		competition.setName("liga division honor");
		competition.setCategory(Ref.create(keyCategories));
		competition.setSport(Ref.create(keySport));
		competitionsManager.add(competition);
		
		List<MatchesVO> matchesList = UtilsLegaSport.parseCalendar(competition);
		try {
			matchesManager.add(matchesList);
			logger.debug("matches inserted");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		try {
			String classificationStr = UtilsLegaSport.classificationStr();
			List<ClassificationEntryVO> classificationList = UtilsLegaSport.parseClassification(classificationStr, competition.getId());
			classificationManager.add(classificationList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/** load users */
		UsersVO usersVO = new UsersVO();
		usersVO.setUsername("antonio.diaz");
		usersVO.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
		usersVO.setAdmin(true);
		usersVO.setBannedUser(false);
		usersVO.setEnabled(true);
		usersVO.setAccountNonExpired(true);
		usersManager.addUser(usersVO);
		
		
		usersManager.removeUser("user.lega");
		usersVO = new UsersVO();
		usersVO.setUsername("user.lega");
		usersVO.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
		usersVO.setAdmin(false);
		usersVO.setBannedUser(false);
		usersVO.setEnabled(true);
		usersVO.setAccountNonExpired(true);
		usersManager.addUser(usersVO);
		
		SportCenter sportsCenter = new SportCenter();
		sportsCenter.setName("Pabellon Europa");
		sportsCenter.setAddress("Av. de Alemania, 2, 28916 Leganés, Madrid");
		Key<SportCenter> centerKey = ofy().save().entity(sportsCenter).now();
		
		SportCourt court = new SportCourt();
		court.setName("Pista central");
		court.setCenter(Ref.create(centerKey));
		court.getSports().add(Ref.create(keySport));
		Key<SportCourt> courtKey = ofy().save().entity(court).now();
		logger.debug("courtKey -->" + courtKey);


		
		List<SportCenter> list = ofy().load().type(SportCenter.class).list();
		for (SportCenter sportCenter : list) {
			logger.debug("Center: " + sportCenter.getName());
			Key<SportCenter> keyCenter = Key.create(SportCenter.class, sportCenter.getId());
			Ref<SportCenter> refCenter = Ref.create(keyCenter);
			List<SportCourt> listCourts = ofy().load().type(SportCourt.class).filter("center", refCenter).list();
			for (SportCourt sportCourt : listCourts) {
				logger.debug("Court: " + sportCourt.getName());
			}			
		}

		logger.debug("finished init...");
		
	}
}
