package com.adiaz.daos;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCenterCourt;
import com.adiaz.entities.Sport;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by toni on 11/07/2017.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class SportCenterCourtDAOImplTest {

	private static final String SPORTCOURT_NAME_1 = "PISTA CENTRAL";
	private static final String SPORTCOURT_NAME_2 = "PISTA SECUNDARIA";
	public static final String LA_CANTERA = "La cantera";
	private static final String PABELLON_EUROPA = "Pabellon Europa";
	public static final String BASKET = "Baloncesto";
	public static final String FUTBOL = "Futbol";
	public static final String LEGANES = "leganes";

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	SportCenterCourtDAO sportCenterCourtDAO;
	@Autowired
	SportCenterDAO sportCenterDAO;
	@Autowired
	SportsDAO sportsDAO;
	@Autowired
	TownDAO townDAO;

	private Ref<SportCenter> centerRefLaCantera;
	private Ref<SportCenter> centerRefPabellonEuropa;
	private Ref<Sport> sportRefFutbol;
	private Ref<Sport> sportRefBasket;
	private Ref<Town> townRefLeganes;


	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(SportCenterCourt.class);
		ObjectifyService.register(SportCenter.class);
		ObjectifyService.register(Sport.class);
		ObjectifyService.register(Town.class);
		/* create town: leganes */
		Town town = new Town();
		town.setName(LEGANES);
		Key<Town> townKey = townDAO.create(town);
		townRefLeganes = Ref.create(townKey);
		/* create center 01*/
		SportCenter sportCenter = new SportCenter();
		sportCenter.setName(LA_CANTERA);
		sportCenter.setTownRef(townRefLeganes);
		Key<SportCenter> sportCenterKey = sportCenterDAO.create(sportCenter);
		centerRefLaCantera = Ref.create(sportCenterKey);
        /* create center 02*/
		sportCenter = new SportCenter();
		sportCenter.setName(PABELLON_EUROPA);
		sportCenter.setTownRef(townRefLeganes);
		sportCenterKey = sportCenterDAO.create(sportCenter);
		centerRefPabellonEuropa = Ref.create(sportCenterKey);
        /* create sport: basket */
		Sport sport = new Sport();
		sport.setName(BASKET);
		Key<Sport> sportKey = sportsDAO.create(sport);
		sportRefBasket = Ref.create(sportKey);
        /* create sport: futbol */
		sport = new Sport();
		sport.setName(FUTBOL);
		sportKey = sportsDAO.create(sport);
		sportRefFutbol = Ref.create(sportKey);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		assertEquals(0, sportCenterCourtDAO.findAllSportCourt().size());
		Key<SportCenterCourt> key = createSportCourt();
		assertEquals(1, sportCenterCourtDAO.findAllSportCourt().size());
		SportCenterCourt sportCenterCourt = sportCenterCourtDAO.findBySportCenter(key.getId());
		assertEquals(centerRefLaCantera, sportCenterCourt.getSportCenterRef());
		assertEquals(LA_CANTERA, centerRefLaCantera.getValue().getName());
		assertEquals(2, sportCenterCourt.getSports().size());
		assertEquals(BASKET, sportCenterCourt.getSports().get(0).getValue().getName());
		assertEquals(FUTBOL, sportCenterCourt.getSports().get(1).getValue().getName());
	}

	@Test
	public void createReturnRef() throws Exception {
		assertEquals(0, sportCenterCourtDAO.findAllSportCourt().size());
		Ref<SportCenterCourt> sportCourtReturnRef = createSportCourtReturnRef();
		assertEquals(1, sportCenterCourtDAO.findAllSportCourt().size());
		assertEquals(SPORTCOURT_NAME_1, sportCourtReturnRef.getValue().getName());
	}

	@Test
	public void updateName() throws Exception {
		Ref<SportCenterCourt> ref = createSportCourtReturnRef();
		SportCenterCourt sportCenterCourt = ref.getValue();
		sportCenterCourt.setName(SPORTCOURT_NAME_2);
		sportCenterCourtDAO.update(sportCenterCourt);
		SportCenterCourt sportCenterCourtUpdated = sportCenterCourtDAO.findBySportCenter(sportCenterCourt.getId());
		assertEquals(SPORTCOURT_NAME_2, sportCenterCourtUpdated.getName());

	}

	@Test
	public void updateCenter() throws Exception {
		SportCenterCourt sportCenterCourt = createSportCourtReturnRef().getValue();
		assertEquals(centerRefLaCantera, sportCenterCourt.getSportCenterRef());
		sportCenterCourt.setSportCenterRef(centerRefPabellonEuropa);
		sportCenterCourtDAO.update(sportCenterCourt);
		assertEquals(centerRefPabellonEuropa, sportCenterCourt.getSportCenterRef());
	}

	@Test
	public void updateSports() throws Exception {
		SportCenterCourt sportCenterCourt = createSportCourtReturnRef().getValue();
		assertEquals(2, sportCenterCourt.getSports().size());
		sportCenterCourt.getSports().remove(0);
		sportCenterCourtDAO.update(sportCenterCourt);
		assertEquals(1, sportCenterCourt.getSports().size());
	}

	@Test
	public void remove() throws Exception {
		SportCenterCourt sportCenterCourt = createSportCourtReturnRef().getValue();
		assertEquals(sportCenterCourt, sportCenterCourtDAO.findBySportCenter(sportCenterCourt.getId()));
		sportCenterCourtDAO.remove(sportCenterCourt);
		assertEquals(null, sportCenterCourtDAO.findBySportCenter(sportCenterCourt.getId()));
	}

	@Test
	public void findAllSportCourt() throws Exception {
		createSportCourt();
		createSportCourt();
		assertEquals(2, sportCenterCourtDAO.findAllSportCourt().size());
	}

	@Test
	public void findSportCourtById() throws Exception {
		Key<SportCenterCourt> sportCourtKey = createSportCourt();
		SportCenterCourt sportCenterCourt = sportCenterCourtDAO.findBySportCenter(sportCourtKey.getId());
		assertEquals(sportCourtKey.getId(), (long) sportCenterCourt.getId());
	}

	@Test
	public void findSportCourtByCenterRef() throws Exception {
		createSportCourt();
		assertEquals(1, sportCenterCourtDAO.findBySportCenter(centerRefLaCantera).size());
		assertEquals(0, sportCenterCourtDAO.findBySportCenter(centerRefPabellonEuropa).size());
	}

	@Test
	public void findSportCourtBySport() throws Exception {
		createSportCourt();
		createSportCourt();
		List<SportCenterCourt> courtList = sportCenterCourtDAO.findBySport(sportRefBasket.get().getId());
		assertEquals(2, courtList.size());

	}


	private Key<SportCenterCourt> createSportCourt() throws Exception {
		SportCenterCourt sportCenterCourt = new SportCenterCourt();
		sportCenterCourt.setName(SPORTCOURT_NAME_1);
		sportCenterCourt.setSportCenterRef(centerRefLaCantera);
		List<Ref<Sport>> list = new ArrayList<>();
		list.add(sportRefFutbol);
		sportCenterCourt.getSports().add(sportRefBasket);
		sportCenterCourt.getSports().add(sportRefFutbol);
		return sportCenterCourtDAO.create(sportCenterCourt);
	}


	private Ref<SportCenterCourt> createSportCourtReturnRef() throws Exception {
		SportCenterCourt sportCenterCourt = new SportCenterCourt();
		sportCenterCourt.setName(SPORTCOURT_NAME_1);
		sportCenterCourt.setSportCenterRef(centerRefLaCantera);
		List<Ref<Sport>> list = new ArrayList<>();
		list.add(sportRefFutbol);
		sportCenterCourt.getSports().add(sportRefBasket);
		sportCenterCourt.getSports().add(sportRefFutbol);
		return sportCenterCourtDAO.createReturnRef(sportCenterCourt);
	}
}