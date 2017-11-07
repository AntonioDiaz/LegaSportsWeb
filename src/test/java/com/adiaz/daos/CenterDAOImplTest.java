package com.adiaz.daos;


import com.adiaz.entities.Center;
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
import static org.junit.Assert.assertTrue;


/**
 * Created by toni on 11/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class CenterDAOImplTest {

	public static final String LEGANES = "LEGANES";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private static final String SPORTCENTER_NAME_1 = "PABELLON EUROPA";
	private static final String SPORTCENTER_NAME_2 = "LA CANTERA";
	private static final String SPORTCENTER_ADDRESS_1 = "Address 01";
	private static final String SPORTCENTER_ADDRESS_2 = "Address 02";


	@Autowired
	private CenterDAO centerDAO;
	@Autowired
	private TownDAO townDAO;
	private Ref<Town> townRef;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Center.class);
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
		assertEquals(0, centerDAO.findAll().size());
		Key<Center> key = createSportCenter();
		assertEquals(1, centerDAO.findAll().size());
		Center center = centerDAO.findById(key.getId());
		assertEquals(key.getId(), (long) center.getId());
		assertEquals(SPORTCENTER_NAME_1, center.getName());
		assertEquals(SPORTCENTER_ADDRESS_1, center.getAddress());
		assertEquals(LEGANES, center.getTownRef().getValue().getName());
	}

	@Test
	public void update() throws Exception {
		Key<Center> key = createSportCenter();
		Center center = centerDAO.findById(key.getId());
		center.setName(SPORTCENTER_NAME_2);
		center.setAddress(SPORTCENTER_ADDRESS_2);
		centerDAO.update(center);
		center = centerDAO.findById(key.getId());
		assertEquals(key.getId(), (long) center.getId());
		assertEquals(SPORTCENTER_NAME_2, center.getName());
		assertEquals(SPORTCENTER_ADDRESS_2, center.getAddress());
	}


	@Test
	public void removeByItem() throws Exception {
		Key<Center> key = createSportCenter();
		Center center = centerDAO.findById(key.getId());
		centerDAO.remove(center.getId());
		assertTrue(centerDAO.findById(center.getId()) == null);
	}

	@Test
	public void removeById() throws Exception {
		Key<Center> key = createSportCenter();
		Center center = centerDAO.findById(key.getId());
		centerDAO.remove(center.getId());
		assertTrue(centerDAO.findById(center.getId()) == null);
	}

	@Test
	public void findAllSportsCenters() throws Exception {
		createSportCenter();
		createSportCenter();
		assertEquals(2, centerDAO.findAll().size());
	}

	@Test
	public void findSportsCenterById() throws Exception {
		Key<Center> key = createSportCenter();
		Center center = centerDAO.findById(key.getId());
		assertEquals(key.getId(), (long) center.getId());
	}

	@Test
	public void findSportsCenterByTown() throws Exception {
		createSportCenter();
		createSportCenter();
		List<Center> centers = centerDAO.findByTown(townRef.get().getId());
		System.out.println(townRef.get().getId());
		assertEquals(2, centers.size());
	}

		private Key<Center> createSportCenter() throws Exception {
		Center center = new Center();
		center.setName(SPORTCENTER_NAME_1);
		center.setAddress(SPORTCENTER_ADDRESS_1);
		center.setTownRef(townRef);
		return centerDAO.create(center);
	}
}