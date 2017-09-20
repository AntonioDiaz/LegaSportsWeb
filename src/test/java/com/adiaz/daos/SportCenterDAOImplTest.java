package com.adiaz.daos;


import com.adiaz.entities.SportCenter;
import com.adiaz.entities.Town;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by toni on 11/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class SportCenterDAOImplTest {

	public static final String LEGANES = "LEGANES";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private static final String SPORTCENTER_NAME_1 = "PABELLON EUROPA";
	private static final String SPORTCENTER_NAME_2 = "LA CANTERA";
	private static final String SPORTCENTER_ADDRESS_1 = "Address 01";
	private static final String SPORTCENTER_ADDRESS_2 = "Address 02";


	@Autowired
	private SportCenterDAO sportCenterDAO;
	@Autowired
	private TownDAO townDAO;
	private Ref<Town> townRef;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(SportCenter.class);
		ObjectifyService.register(Town.class);
		Town town = new Town();
		town.setName(LEGANES);
		Key<Town> townKey = townDAO.create(town);
		townRef = Ref.create(townKey);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		assertEquals(0, sportCenterDAO.findAllSportsCenters().size());
		Key<SportCenter> key = createSportCenter();
		assertEquals(1, sportCenterDAO.findAllSportsCenters().size());
		SportCenter sportCenter = sportCenterDAO.findSportsCenterById(key.getId());
		assertEquals(key.getId(), (long) sportCenter.getId());
		assertEquals(SPORTCENTER_NAME_1, sportCenter.getName());
		assertEquals(SPORTCENTER_ADDRESS_1, sportCenter.getAddress());
		assertEquals(LEGANES, sportCenter.getTownRef().getValue().getName());
	}

	@Test
	public void update() throws Exception {
		Key<SportCenter> key = createSportCenter();
		SportCenter sportCenter = sportCenterDAO.findSportsCenterById(key.getId());
		sportCenter.setName(SPORTCENTER_NAME_2);
		sportCenter.setAddress(SPORTCENTER_ADDRESS_2);
		sportCenterDAO.update(sportCenter);
		sportCenter = sportCenterDAO.findSportsCenterById(key.getId());
		assertEquals(key.getId(), (long) sportCenter.getId());
		assertEquals(SPORTCENTER_NAME_2, sportCenter.getName());
		assertEquals(SPORTCENTER_ADDRESS_2, sportCenter.getAddress());
	}


	@Test
	public void removeByItem() throws Exception {
		Key<SportCenter> key = createSportCenter();
		SportCenter sportCenter = sportCenterDAO.findSportsCenterById(key.getId());
		sportCenterDAO.remove(sportCenter.getId());
		assertTrue(sportCenterDAO.findSportsCenterById(sportCenter.getId()) == null);
	}

	@Test
	public void removeById() throws Exception {
		Key<SportCenter> key = createSportCenter();
		SportCenter sportCenter = sportCenterDAO.findSportsCenterById(key.getId());
		sportCenterDAO.remove(sportCenter.getId());
		assertTrue(sportCenterDAO.findSportsCenterById(sportCenter.getId()) == null);
	}

	@Test
	public void findAllSportsCenters() throws Exception {
		createSportCenter();
		createSportCenter();
		assertEquals(2, sportCenterDAO.findAllSportsCenters().size());
	}

	@Test
	public void findSportsCenterById() throws Exception {
		Key<SportCenter> key = createSportCenter();
		SportCenter sportCenter = sportCenterDAO.findSportsCenterById(key.getId());
		assertEquals(key.getId(), (long) sportCenter.getId());
	}

	@Test
	public void findSportsCenterByTown() throws Exception {
		createSportCenter();
		createSportCenter();
		List<SportCenter> centers = sportCenterDAO.findSportsCenterByTown(townRef.get().getId());
		System.out.println(townRef.get().getId());
		assertEquals(2, centers.size());
	}

		private Key<SportCenter> createSportCenter() throws Exception {
		SportCenter sportCenter = new SportCenter();
		sportCenter.setName(SPORTCENTER_NAME_1);
		sportCenter.setAddress(SPORTCENTER_ADDRESS_1);
		sportCenter.setTownRef(townRef);
		return sportCenterDAO.create(sportCenter);
	}
}