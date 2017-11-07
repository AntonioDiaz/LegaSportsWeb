package com.adiaz.daos;

import com.adiaz.entities.Club;
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

import static org.junit.Assert.*;

/**
 * Created by toni on 24/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class ClubDAOImplTest {

	public static final String ATLETICO_MADRID = "ATLETICO MADRID";
	public static final String CD_LEGANES = "CD LEGANES";
	public static final String LEGANES = "LEGANES";
	public static final String FUENLABRADA = "FUENLABRADA";
	public static final String CD_FUENLABRADA = "CD FUENLABRADA";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	ClubDAO clubDAO;
	@Autowired
	TownDAO townDAO;
	private Ref<Town> leganesRef;
	private Ref<Town> fuenlaRef;


	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Club.class);
		ObjectifyService.register(Town.class);
		Town town = new Town();
		town.setName(LEGANES);
		Key<Town> townKey = townDAO.create(town);
		leganesRef = Ref.create(townKey);
		town = new Town();
		town.setName(FUENLABRADA);
		townKey = townDAO.create(town);
		fuenlaRef = Ref.create(townKey);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		Key<Club> key = createClub(ATLETICO_MADRID, leganesRef);
		Club club = clubDAO.findById(key.getId());
		assertEquals(ATLETICO_MADRID, club.getName());
		assertEquals(leganesRef, club.getTownRef());
	}

	@Test
	public void update() throws Exception {
		Key<Club> key = createClub(ATLETICO_MADRID);
		Club club = Ref.create(key).get();
		club.setName(CD_LEGANES);
		clubDAO.update(club);
		club = clubDAO.findById(key.getId());
		assertEquals(CD_LEGANES, club.getName());
	}

	@Test
	public void remove() throws Exception {
		Key<Club> key = createClub(ATLETICO_MADRID);
		assertEquals(1, clubDAO.findAll().size());
		Club club = new Club();
		club.setId(key.getId());
		clubDAO.remove(club);
		assertEquals(0, clubDAO.findAll().size());
	}

	@Test
	public void findById() throws Exception {
		Key<Club> key = createClub(ATLETICO_MADRID);
		assertEquals(key.getId(), (long)clubDAO.findById(key.getId()).getId());
	}

	@Test
	public void findAll() throws Exception {
		createClub(ATLETICO_MADRID);
		createClub(CD_LEGANES);
		assertEquals(2, clubDAO.findAll().size());
	}

	@Test
	public void findByTownId() throws Exception {
		createClub(ATLETICO_MADRID, leganesRef);
		createClub(CD_LEGANES, leganesRef);
		createClub(CD_FUENLABRADA, fuenlaRef);
		assertEquals(3, clubDAO.findAll().size());
		assertEquals(2, clubDAO.findByTown(leganesRef.key().getId()).size());
		assertEquals(1, clubDAO.findByTown(fuenlaRef.key().getId()).size());
	}

	private Key<Club> createClub(String name, Ref<Town> refTown) throws Exception {
		Club club = new Club();
		club.setName(name);
		club.setTownRef(refTown);
		return clubDAO.create(club);

	}
	private Key<Club> createClub(String name) throws Exception {
		return createClub(name, leganesRef);
	}

}