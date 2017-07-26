package com.adiaz.daos;

import com.adiaz.entities.Category;
import com.adiaz.entities.Club;
import com.adiaz.entities.Team;
import com.adiaz.entities.Town;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Category.class);
		ObjectifyService.register(Town.class);
		ObjectifyService.register(Club.class);
		ObjectifyService.register(Team.class);
		Town town = new Town();
		town.setName(LEGANES);
		refLeganes = Ref.create (townDAO.create(town));

		town = new Town();
		town.setName(FUENLABRADA);
		refFuenlabrada= Ref.create (townDAO.create(town));


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

	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		assertEquals(1, teamDAO.findAll().size());
		Team team = teamDAO.findById(key.getId());
		assertEquals(CD_LEGANES_CADETE, team.getName());
		assertEquals(refCdLeganes, team.getClubRef());
		assertEquals(refCadete, team.getCategoryRef());
	}

	@Test
	public void update() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		Team team = teamDAO.findById(key.getId());
		team.setCategoryRef(refJuvenil);
		teamDAO.update(team);
		team = teamDAO.findById(key.getId());
		assertEquals(refJuvenil, team.getCategoryRef());
	}

	@Test
	public void remove() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		Team team = teamDAO.findById(key.getId());
		assertNotNull(team);
		teamDAO.remove(team);
		assertNull(teamDAO.findById(key.getId()));

	}

	@Test
	public void findAll() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		createTeam(CD_LEGANES_JUVENIL, refCdLeganes, refJuvenil, refLeganes);
		assertEquals(2, teamDAO.findAll().size());

	}

	@Test
	public void findById() throws Exception {
		Key<Team> key = createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		Team team = teamDAO.findById(key.getId());
		assertEquals(key.getId(), (long)team.getId());
		assertNull(teamDAO.findById(666l));
	}

	@Test
	public void find_filterbycategory() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes);
		List<Team> cadeteTeams = teamDAO.find(null, refCadete.get().getId());
		assertEquals(2, cadeteTeams.size());
	}

	@Test
	public void find_testOrder() throws Exception {
		createTeam("B", refCdLeganes, refCadete, refLeganes);
		createTeam("Z", refCdLeganes, refCadete, refLeganes);
		createTeam("A", refCdLeganes, refJuvenil, refLeganes);
		List<Team> cadeteTeams = teamDAO.find(null, null);
		assertEquals(3, cadeteTeams.size());
		assertEquals("A", cadeteTeams.get(0).getName());
		assertEquals("B", cadeteTeams.get(1).getName());
		assertEquals("Z", cadeteTeams.get(2).getName());
	}

	@Test
	public void find_filterByTown() throws Exception {
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refCadete, refLeganes);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refLeganes);
		createTeam(CD_LEGANES_CADETE, refCdLeganes, refJuvenil, refFuenlabrada);
		List<Team> teams = teamDAO.find(refLeganes.get().getId(), refJuvenil.get().getId());
		assertEquals(1, teams.size());
	}

	private Key<Team> createTeam(String teamName, Ref<Club> refCdLeganes, Ref<Category> refCadete, Ref<Town> refTown) throws Exception {
		Team team = new Team();
		team.setName(teamName);
		team.setClubRef(refCdLeganes);
		team.setCategoryRef(refCadete);
		team.setTownRef(refTown);
		return teamDAO.create(team);
	}
}