package com.adiaz.utils;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.adiaz.entities.CategoriesVO;
import com.adiaz.entities.ClassificationEntryVO;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.entities.SportVO;
import com.adiaz.entities.UsersVO;
import com.adiaz.services.CategoriesManager;
import com.adiaz.services.CompetitionsManager;
import com.adiaz.services.SportsManager;
import com.adiaz.services.UsersManager;
import com.googlecode.objectify.ObjectifyService;

public class RegisterEntities {

	private static final Logger logger = Logger.getLogger(RegisterEntities.class.getName());
	
	@Autowired SportsManager sportsManager;
	@Autowired CategoriesManager categoriesManager;
	@Autowired CompetitionsManager competitionsManager;
	@Autowired UsersManager usersManager;
	
	
	public void init() {
		ObjectifyService.register(SportVO.class);
		ObjectifyService.register(CategoriesVO.class);
		ObjectifyService.register(CompetitionsVO.class);
		ObjectifyService.register(MatchesVO.class);
		ObjectifyService.register(ClassificationEntryVO.class);
		ObjectifyService.register(UsersVO.class);
		/* Adding index: it is necesary to update each entity.*/
		List<CompetitionsVO> queryCompetitions = competitionsManager.queryCompetitions();
		for (CompetitionsVO competitionsVO : queryCompetitions) {
			try {
				competitionsManager.update(competitionsVO);
			} catch (Exception e) {	
				e.printStackTrace();
			}
		}
		try {
			usersManager.removeUser("antonio.diaz");
			UsersVO usersVO = new UsersVO();
			usersVO.setUsername("antonio.diaz");
			usersVO.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
			usersVO.setAdmin(true);
			usersVO.setBannedUser(false);
			usersVO.setEnabled(true);
			usersVO.setAccountNonExpired(true);
			usersManager.addUser(usersVO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
