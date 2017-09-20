package com.adiaz.daos;

import com.adiaz.entities.*;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by toni on 11/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class CompetitionsDAOImplTest {
	private static final String COPA_PRIMAVERA = "COPA_PRIMAVERA";
	private static final String COPA_LIGA = "COPA_LIGA";
	public static final String LEGANES = "Leganes";
	public static final String FUENLABRADA = "Fuenlabrada";
	public static final String ATLETI = "Atleti";
	public static final String CHURRIGUERA = "Churriguera";
	public static final String LEGAMAR = "Legamar";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	CompetitionsDAO competitionsDAO;
	@Autowired
	CategoriesDAO categoriesDAO;
	@Autowired
	SportsDAO sportsDAO;
	@Autowired
	TownDAO townDAO;
	@Autowired
	TeamDAO teamDAO;

	private Ref<Category> refCategoryCadete;
	private Ref<Sport> refSportBasket;
	private Ref<Sport> refSportFutbol;
	private Ref<Town> refTownLeganes;
	private Ref<Town> refTownFuenlabrada;
	private Ref<Team> refAtleti;
	private Ref<Team> refLegamar;
	private Ref<Team> refChurriguera;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Competition.class);
		ObjectifyService.register(Category.class);
		ObjectifyService.register(Sport.class);
		ObjectifyService.register(Town.class);
		ObjectifyService.register(Team.class);
		/*category */
		Category category = new Category();
		category.setName("Cadete");
		Key<Category> categoryKey = categoriesDAO.create(category);
		refCategoryCadete = Ref.create(categoryKey);
		/*sport: basket */
		Sport sport = new Sport();
		sport.setName("Basket");
		Key<Sport> sportKey = sportsDAO.create(sport);
		refSportBasket = Ref.create(sportKey);
		/*sport: futbol */
		sport = new Sport();
		sport.setName("Futbol");
		sportKey = sportsDAO.create(sport);
		refSportFutbol = Ref.create(sportKey);
		/*town: leganes*/
		Town town = new Town();
		town.setName(LEGANES);
		Key<Town> townKey = townDAO.create(town);
		refTownLeganes = Ref.create(townKey);
		/*town: fuenlabrada*/
		town = new Town();
		town.setName(FUENLABRADA);
		townKey = townDAO.create(town);
		refTownFuenlabrada = Ref.create(townKey);
		/*team: Atleti*/
		Team team = new Team();
		team.setName(ATLETI);
		refAtleti = Ref.create(teamDAO.create(team));
		/*team: Churriguera*/
		team.setName(CHURRIGUERA);
		refChurriguera= Ref.create(teamDAO.create(team));
		/*team: Legamar*/
		team.setName(LEGAMAR);
		refLegamar = Ref.create(teamDAO.create(team));

	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		Key<Competition> key = createCompetition(COPA_PRIMAVERA);
		Competition competition = Ref.create(key).getValue();
		assertEquals(competition, competitionsDAO.findById(competition.getId()));
	}

	@Test
	public void updateName() throws Exception {
		Key<Competition> key = createCompetition(COPA_PRIMAVERA);
		Competition competition = Ref.create(key).getValue();
		competition.setName(COPA_LIGA);
		competitionsDAO.update(competition);
		competition = competitionsDAO.findById(competition.getId());
		assertEquals(COPA_LIGA, competition.getName());
	}

	@Test
	public void update_sport() throws Exception {
		Key<Competition> key = createCompetition(COPA_PRIMAVERA);
		Competition competition = Ref.create(key).getValue();
		competition.setSportRef(refSportFutbol);
		competitionsDAO.update(competition);
		competition = competitionsDAO.findById(competition.getId());
		assertEquals(refSportFutbol, competition.getSportRef());
		competition.getRefs();
		assertEquals("Futbol", competition.getSportEntity().getName());
	}

	@Test
	public void update_town() throws Exception {
		Key<Competition> key = createCompetition(refTownLeganes);
		Competition competition = competitionsDAO.findById(key.getId());
		assertEquals(key.getId(), (long)competition.getId());
		assertEquals(LEGANES, competition.getTownRef().get().getName());
		competition.setTownRef(refTownFuenlabrada);
		competitionsDAO.update(competition);
		competition = competitionsDAO.findById(key.getId());
		assertEquals(FUENLABRADA, competition.getTownRef().get().getName());
	}

	@Test
	public void remove() throws Exception {
		Key<Competition> key = createCompetition(COPA_PRIMAVERA);
		Competition competition = Ref.create(key).getValue();
		competitionsDAO.remove(competition);
		assertEquals(0, competitionsDAO.findAll().size());
	}

	@Test
	public void findCompetitions() throws Exception {
		createCompetition(COPA_PRIMAVERA);
		createCompetition(COPA_LIGA);
		assertEquals(2, competitionsDAO.findAll().size());
	}

	@Test
	public void findCompetitionsBySport() throws Exception {
		createCompetition(COPA_PRIMAVERA, refSportFutbol);
		createCompetition(COPA_LIGA, refSportFutbol);
		createCompetition(COPA_LIGA, refSportBasket);
		assertEquals(2, competitionsDAO.findBySport(refSportFutbol.getValue().getId()).size());
	}

	@Test
	public void findCompetitionsBySportAndCategory() throws Exception {
		createCompetition(COPA_PRIMAVERA);
		createCompetition(COPA_LIGA);
		long idCategory = refCategoryCadete.getKey().getId();
		long idSport = refSportBasket.getKey().getId();
		assertEquals(2, competitionsDAO.find(idSport, idCategory, null).size());
	}

	@Test
	public void findCompetitionsBySportAndCategory_filterBySport() throws Exception {
		createCompetition(COPA_PRIMAVERA, refSportBasket);
		createCompetition(COPA_LIGA, refSportFutbol);
		createCompetition(COPA_LIGA, refSportFutbol);
		long idCategory = refCategoryCadete.getKey().getId();
		long idSport = refSportBasket.getKey().getId();
		assertEquals(1, competitionsDAO.find(idSport, idCategory, null).size());
	}

	@Test
	public void findCompetitionsBySportAndCategory_filterByTown() throws Exception {
		createCompetition(refTownLeganes);
		createCompetition(refTownLeganes);
		createCompetition(refTownFuenlabrada);
		Long idFuenla = refTownFuenlabrada.get().getId();
		Long idLeganes = refTownLeganes.get().getId();
		Long idFutbol = refSportFutbol.get().getId();
		assertEquals(1, competitionsDAO.find(null, null, idFuenla).size());
		assertEquals(2, competitionsDAO.find(null, null, idLeganes).size());
		assertEquals(0, competitionsDAO.find(idFutbol, null, idFuenla).size());
	}

	@Test
	public void findCompetitionsBySportAndCategory_filterPublised() throws Exception {
		Key<Competition> key = createCompetition(refTownLeganes);
		assertEquals(1, competitionsDAO.find(null, null, null).size());
		assertEquals(0, competitionsDAO.find(refTownLeganes.getKey().getId(), true).size());
		assertEquals(1, competitionsDAO.find(refTownLeganes.getKey().getId(), false).size());
		Competition competition = Ref.create(key).getValue();
		competition.setLastPublished(new Date());
		competitionsDAO.update(competition);
		assertEquals(1, competitionsDAO.find(refTownLeganes.getKey().getId(), true).size());
	}

	@Test
	public void findCompetitionsById() throws Exception {
		Key<Competition> key = createCompetition(COPA_PRIMAVERA);
		Competition competition = Ref.create(key).getValue();
		assertEquals(competition, competitionsDAO.findById(key.getId()));
	}

	@Test
	public void findCompetitionsByCategory() throws Exception {
		List<Competition> competitionsByCategory = competitionsDAO.findByCategory(refCategoryCadete.get().getId());
		assertEquals(0, competitionsByCategory.size());
		createCompetition(COPA_LIGA);
		createCompetition(COPA_PRIMAVERA);
		competitionsByCategory = competitionsDAO.findByCategory(refCategoryCadete.get().getId());
		assertEquals(2, competitionsByCategory.size());
	}

	@Test
	public void findCompetitionsByTown() throws Exception {
		assertEquals(0, competitionsDAO.findByTown(refTownLeganes.get().getId()).size());
		createCompetition(COPA_LIGA);
		createCompetition(COPA_PRIMAVERA);
		assertEquals(2, competitionsDAO.findByTown(refTownLeganes.get().getId()).size());
	}

	@Test
	public void findCompetitionsByTeam() throws Exception {
		assertEquals(0, competitionsDAO.findByTeam(refAtleti.get().getId()).size());
		createCompetition(COPA_LIGA);
		createCompetition(COPA_PRIMAVERA);
		assertEquals(2, competitionsDAO.findByTown(refTownLeganes.get().getId()).size());
	}


	private Key<Competition> createCompetition(
			String competitionName, Ref<Sport> refSport, Ref<Category> refCategory, Ref<Town> refTown) throws Exception {
		Competition competition = new Competition();
		competition.setName(competitionName);
		competition.setSportRef(refSport);
		competition.setCategoryRef(refCategory);
		competition.setTownRef(refTown);
		List<Ref<Team>> teamsRef = new ArrayList<>();
		teamsRef.add(refAtleti);
		teamsRef.add(refLegamar);
		teamsRef.add(refChurriguera);
		competition.setTeams(teamsRef);
		return competitionsDAO.create(competition);
	}

	private Key<Competition> createCompetition(String competitionName) throws Exception {
		return createCompetition(competitionName, refSportBasket, refCategoryCadete, refTownLeganes);
	}
	private Key<Competition> createCompetition(Ref<Town> townRef) throws Exception {
		return createCompetition(COPA_LIGA, refSportBasket, refCategoryCadete, townRef);
	}

	private Key<Competition> createCompetition(String competitionName, Ref<Sport> sportRef) throws Exception {
		return createCompetition(competitionName, sportRef, refCategoryCadete, refTownLeganes);
	}
}