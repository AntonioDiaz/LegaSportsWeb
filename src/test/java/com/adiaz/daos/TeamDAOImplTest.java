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

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by toni on 25/07/2017.
 */
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamDAOImplTest {

	public static final String CD_LEGANES = "CD LEGANES";
	public static final String CD_LEGANES_CADETE = "CD LEGANES CADETE";
	public static final String CD_LEGANES_JUVENIL = "CD LEGANES JUVENIL";
	public static final String CADETE = "Cadete";
	public static final String JUVENIL = "juvenil";
	public static final String LEGANES = "LEGANES";
	public static final String FUENLABRADA = "FUENLABRADA";
	public static final String BALONCESTO = "Baloncesto";
	public static final String SOCCER = "SOCCER";

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private Ref<Club> refCdLeganes;
	private Ref<Town> refLeganes;
	private Ref<Town> refFuenlabrada;
	private Ref<Category> refCadete;
	private Ref<Category> refJuvenil;

	@Autowired
	ClubDAO clubDAO;

	@Autowired
	CategoriesDAO categoriesDAO;

	@Autowired
	TeamDAO teamDAO;

	@Autowired
	TownDAO townDAO;

	@Autowired
	SportsDAO sportsDAO;

	private Ref<Sport> basketRef;
	private Ref<Sport> soccerRef;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Category.class);
		ObjectifyService.register(Town.class);
		ObjectifyService.register(Club.class);
		ObjectifyService.register(Team.class);
		ObjectifyService.register(Sport.class);
		Town town = new Town();
		town.setName(LEGANES);
		refLeganes = Ref.create(townDAO.create(town));

		town = new Town();
		town.setName(FUENLABRADA);
		refFuenlabrada = Ref.create(townDAO.create(town));


		Club club = new Club();
		club.setName(CD_LEGANES);
		club.setTownRef(refLeganes);
		Key<Club> clubKey = clubDAO.create(club);
		refCdLeganes = Ref.create(clubKey);

		Category category = new Category();
		category.setName(CADETE);
		Key<Category> categoryKey = categoriesDAO.create(category);
		refCadete = Ref.create(categoryKey);

		category = new Category();
		category.setName(JUVENIL);
		categoryKey = categoriesDAO.create(category);
		refJuvenil = Ref.create(categoryKey);

		Sport sport = new Sport();
		sport.setName(BALONCESTO);
		Key<Sport> sportKey = sportsDAO.create(sport);
		basketRef = Ref.create(sportKey);

		sport = new Sport();
		sport.setName(SOCCER);
		soccerRef = Ref.create(sportsDAO.create(sport));

	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		assertEquals(1, teamDAO.findAll().size());
		Team team = teamDAO.findById(key.getId());
		assertEquals(CD_LEGANES_CADETE, team.getName());
		assertEquals(refCdLeganes, team.getClubRef());
		assertEquals(refCadete, team.getCategoryRef());
	}

	@Test
	public void update() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		Team team = teamDAO.findById(key.getId());
		team.setCategoryRef(refJuvenil);
		teamDAO.update(team);
		team = teamDAO.findById(key.getId());
		assertEquals(refJuvenil, team.getCategoryRef());
	}

	@Test
	public void remove() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		Team team = teamDAO.findById(key.getId());
		assertNotNull(team);
		teamDAO.remove(team);
		assertNull(teamDAO.findById(key.getId()));

	}

	@Test
	public void findAll() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		createTeam(CD_LEGANES_JUVENIL, refCdLeganes, refJuvenil, refLeganes, basketRef);
		assertEquals(2, teamDAO.findAll().size());

	}

	@Test
	public void findById() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		Team team = teamDAO.findById(key.getId());
		assertEquals(key.getId(), (long) team.getId());
		assertNull(teamDAO.findById(666l));
	}

	@Test
	public void find_filterbycategory() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, basketRef);
		List<Team> cadeteTeams = teamDAO.find(null, refCadete.get().getId(), null);
		assertEquals(2, cadeteTeams.size());
	}

	@Test
	public void find_testOrder() throws Exception {
		createTeam("B", refCdLeganes, refCadete, refLeganes, basketRef);
		createTeam("Z", refCdLeganes, refCadete, refLeganes, basketRef);
		createTeam("A", refCdLeganes, refJuvenil, refLeganes, basketRef);
		List<Team> cadeteTeams = teamDAO.find(null, null, null);
		assertEquals(3, cadeteTeams.size());
		assertEquals("A", cadeteTeams.get(0).getName());
		assertEquals("B", cadeteTeams.get(1).getName());
		assertEquals("Z", cadeteTeams.get(2).getName());
	}

	@Test
	public void find_filterByTown() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, basketRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, basketRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refFuenlabrada, basketRef);
		List<Team> teams = teamDAO.find(refLeganes.get().getId(), refJuvenil.get().getId(), null);
		assertEquals(1, teams.size());
	}

	@Test
	public void find_filterBySport() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, soccerRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, soccerRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refFuenlabrada, basketRef);
		List<Team> teams = teamDAO.find(null, null, soccerRef.get().getId());
		assertEquals(2, teams.size());
	}

	@Test
	public void find_filterByName() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, soccerRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, soccerRef);
		createTeam(CD_LEGANES_JUVENIL, refCdLeganes, refJuvenil, refFuenlabrada, basketRef);
		List<Team> teams = teamDAO.find(null, null, null, CD_LEGANES_CADETE);
		assertEquals(2, teams.size());
		teams = teamDAO.find(null, null, null, CD_LEGANES_JUVENIL);
		assertEquals(1, teams.size());
	}

	private Key<Team> createTeam(
			String teamName, Ref<Club> refCdLeganes, Ref<Category> refCadete, Ref<Town> refTown, Ref<Sport> refSport) throws Exception {
		Team team = new Team();
		team.setName(teamName);
		team.setClubRef(refCdLeganes);
		team.setCategoryRef(refCadete);
		team.setTownRef(refTown);
		team.setSportRef(refSport);
		return teamDAO.create(team);
	}

	@Test
	public void findbysport() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, soccerRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, soccerRef);
		createTeam(CD_LEGANES_JUVENIL, refCdLeganes, refJuvenil, refFuenlabrada, basketRef);
		List<Team> teamsSoccer = teamDAO.findBySport(soccerRef.get().getId());
		List<Team> teamsBasket = teamDAO.findBySport(basketRef.get().getId());
		assertEquals(2, teamsSoccer.size());
		assertEquals(1, teamsBasket.size());
	}

	@Test
	public void findbycategory() throws Exception {
		List<Team> byCategory = teamDAO.findByCategory(refJuvenil.get().getId());
		assertTrue(byCategory.isEmpty());
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, soccerRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, soccerRef);
		createTeam(CD_LEGANES_JUVENIL, refCdLeganes, refJuvenil, refFuenlabrada, basketRef);
		assertEquals(1, teamDAO.findByCategory(refCadete.get().getId()).size());
		assertEquals(2, teamDAO.findByCategory(refJuvenil.get().getId()).size());
	}

	@Test
	public void findbyclub() throws Exception {
		assertEquals(0, teamDAO.findByClub(refCdLeganes.get().getId()).size());
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, soccerRef);
		assertEquals(1, teamDAO.findByClub(refCdLeganes.get().getId()).size());
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, soccerRef);
		assertEquals(2, teamDAO.findByClub(refCdLeganes.get().getId()).size());
	}

	@Test
	public void findByTown() throws Exception {
		List<Team> byCategory = teamDAO.findByTown(refLeganes.get().getId());
		assertTrue(byCategory.isEmpty());
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes, soccerRef);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes, soccerRef);
		createTeam(CD_LEGANES_JUVENIL, refCdLeganes, refJuvenil, refFuenlabrada, basketRef);
		assertEquals(1, teamDAO.findByTown(refFuenlabrada.get().getId()).size());
		assertEquals(2, teamDAO.findByTown(refLeganes.get().getId()).size());
	}
}