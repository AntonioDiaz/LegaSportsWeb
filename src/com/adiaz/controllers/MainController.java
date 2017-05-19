package com.adiaz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.adiaz.entities.CategoriesVO;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.entities.SportVO;
import com.adiaz.services.CategoriesManager;
import com.adiaz.services.CompetitionsManager;
import com.adiaz.services.MatchesManager;
import com.adiaz.services.SportsManager;
import com.adiaz.utils.ConstantsLegaSport;
import com.adiaz.utils.UtilsLegaSport;
import com.googlecode.objectify.Ref;

@Controller
public class MainController {

	@Autowired
	SportsManager sportsManager;
	@Autowired
	CategoriesManager categoriesManager;
	@Autowired
	CompetitionsManager competitionsManager;
	@Autowired
	MatchesManager matchesManager;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(ModelMap modelMap) {
		return "home";
	}

	@RequestMapping(value = "/cleanDB", method = RequestMethod.GET)
	public String cleanDB(ModelMap modelMap) {
		String operationResult;
		try {
			for (MatchesVO matchesVO: matchesManager.queryMatches()) {
				matchesManager.remove(matchesVO);
			}
			for (SportVO sportVO : sportsManager.querySports()) {
				sportsManager.remove(sportVO);
			}
			for (CategoriesVO categoriesVO : categoriesManager.queryCategories()) {
				categoriesManager.remove(categoriesVO);
			}
			for (CompetitionsVO competitionsVO : competitionsManager.queryCompetitions()) {
				competitionsManager.remove(competitionsVO);
			}			
			operationResult = "clean done :)";
		} catch (Exception e) {
			e.printStackTrace();
			operationResult = e.getMessage();
		}
		modelMap.addAttribute("message", operationResult);
		return "home";
	}

	@RequestMapping(value = "/loadSports", method = RequestMethod.GET)
	public String loadSports(ModelMap modelMap) {
		String operationResult;
		try {
			for (String sportName : ConstantsLegaSport.SPORTS_NAMES) {
				sportsManager.add(new SportVO(sportName));
			}
			operationResult = "Insercion de sports ok";
		} catch (Exception e) {
			e.printStackTrace();
			operationResult = e.getMessage();
		}
		modelMap.addAttribute("message", operationResult);
		return "home";
	}

	@RequestMapping(value = "/loadCategories", method = RequestMethod.GET)
	public String loadCategories(ModelMap modelMap) {
		String operationResult;
		try {
			String[] categoriesNames = ConstantsLegaSport.CATEGORIES_NAMES;
			int order = 0;
			for (String name : categoriesNames) {
				CategoriesVO categoriesVO = new CategoriesVO();
				categoriesVO.setName(name);
				categoriesVO.setOrder(order++);
				categoriesManager.add(categoriesVO);
			}
			operationResult = "Inserto las categorias";
		} catch (Exception e) {
			e.printStackTrace();
			operationResult = e.getMessage();
		}
		modelMap.addAttribute("message", operationResult);
		return "home";
	}

	@RequestMapping(value = "/loadCompetitions", method = RequestMethod.GET)
	public String loadCompetitions(ModelMap modelMap) {
		String operationResult = "loadCompetitions done";
		modelMap.addAttribute("message", operationResult);
		SportVO sportVO = sportsManager.querySportsByName("Voleibol");
		CategoriesVO category = categoriesManager.queryCategoriesByName("Alevin");
		CompetitionsVO competition = new CompetitionsVO();
		try {
			competition.setName("liga division honor");
			competition.setCategory(Ref.create(category));
			competition.setSport(Ref.create(sportVO));
			competitionsManager.add(competition);
			operationResult = "competition insert done.";
		} catch (Exception e) {
			operationResult = "inserting error.";
			e.printStackTrace();
		}
		return "home";
	}

	@RequestMapping(value = "/loadMatches", method = RequestMethod.GET)
	public ModelAndView loadMatches(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView("home");
		List<CompetitionsVO> queryCompetitions = competitionsManager.queryCompetitions();
		if (queryCompetitions.size()>0) {
			List<MatchesVO> matchesList = UtilsLegaSport.parseCalendar(queryCompetitions.get(0));
			try {
				matchesManager.add(matchesList);
				modelAndView.addObject("message", "matches inserted.");
			} catch (Exception e) {
				modelAndView.addObject("message", "error on insert matches.");
				e.printStackTrace();
			}
		}
		return modelAndView;
	}
	
	// @Autowired
	// ServletContext servletContext;

	// @RequestMapping(value = "/loadCategoriesFromFiles", method =
	// RequestMethod.GET)
	// public String loadCategoriesFromFiles(ModelMap modelMap) {
	// String operationResult = "";
	// InputStream inputStream = null;
	// try {
	// inputStream =
	// servletContext.getResourceAsStream("/WEB-INF/static_data/volleybol.txt");
	// BufferedReader bufferedReader = new BufferedReader(new
	// InputStreamReader(inputStream));
	// SportVO sportVO = sportsManager.querySportsByName("Voleibol");
	// String line;
	// while ((line = bufferedReader.readLine()) != null) {
	// CategoriesVO categoriesVO = new CategoriesVO();
	// categoriesVO.setName(line);
	// categoriesVO.setSport(sportVO);
	// categoriesManager.add(categoriesVO);
	// }
	// operationResult = "load data from file finished.";
	// } catch (IOException e) {
	// operationResult = "reading error";
	// e.printStackTrace();
	// } catch (Exception e) {
	// operationResult = "inserting error";
	// e.printStackTrace();
	// } finally {
	// if (inputStream != null) {
	// try {
	// inputStream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// modelMap.addAttribute("message", operationResult);
	// return "hello";
	// }

}
