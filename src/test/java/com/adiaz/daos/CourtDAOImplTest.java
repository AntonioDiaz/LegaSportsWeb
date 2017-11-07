package com.adiaz.daos;

import com.adiaz.entities.Center;
import com.adiaz.entities.Court;
import com.adiaz.entities.Sport;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by toni on 11/07/2017.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class CourtDAOImplTest {

	private static final String SPORTCOURT_NAME_1 = "PISTA CENTRAL";
	private static final String SPORTCOURT_NAME_2 = "PISTA SECUNDARIA";
	public static final String LA_CANTERA = "La cantera";
	private static final String PABELLON_EUROPA = "Pabellon Europa";
	public static final String BASKET = "Baloncesto";
	public static final String FUTBOL = "Futbol";
	public static final String LEGANES = "leganes";

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	CourtDAO courtDAO;
	@Autowired
	CenterDAO centerDAO;
	@Autowired
	SportsDAO sportsDAO;
	@Autowired
	TownDAO townDAO;

	private Ref<Center> centerRefLaCantera;
	private Ref<Center> centerRefPabellonEuropa;
	private Ref<Sport> sportRefFutbol;
	private Ref<Sport> sportRefBasket;
	private Ref<Town> townRefLeganes;


	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Court.class);
		ObjectifyService.register(Center.class);
		ObjectifyService.register(Sport.class);
		ObjectifyService.register(Town.class);
		/* create town: leganes */
		Town town = new Town();
		town.setName(LEGANES);
		Key<Town> townKey = townDAO.create(town);
		townRefLeganes = Ref.create(townKey);
		/* create center 01*/
		Center center = new Center();
		center.setName(LA_CANTERA);
		center.setTownRef(townRefLeganes);
		Key<Center> sportCenterKey = centerDAO.create(center);
		centerRefLaCantera = Ref.create(sportCenterKey);
        /* create center 02*/
		center = new Center();
		center.setName(PABELLON_EUROPA);
		center.setTownRef(townRefLeganes);
		sportCenterKey = centerDAO.create(center);
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
		assertEquals(0, courtDAO.findAll().size());
		Key<Court> key = createSportCourt();
		assertEquals(1, courtDAO.findAll().size());
		Court court = courtDAO.findById(key.getId());
		assertEquals(centerRefLaCantera, court.getCenterRef());
		assertEquals(LA_CANTERA, centerRefLaCantera.getValue().getName());
		assertEquals(2, court.getSports().size());
		assertEquals(BASKET, court.getSports().get(0).getValue().getName());
		assertEquals(FUTBOL, court.getSports().get(1).getValue().getName());
	}

	@Test
	public void createReturnRef() throws Exception {
		assertEquals(0, courtDAO.findAll().size());
		Ref<Court> sportCourtReturnRef = createSportCourtReturnRef();
		assertEquals(1, courtDAO.findAll().size());
		assertEquals(SPORTCOURT_NAME_1, sportCourtReturnRef.getValue().getName());
	}

	@Test
	public void updateName() throws Exception {
		Ref<Court> ref = createSportCourtReturnRef();
		Court court = ref.getValue();
		court.setName(SPORTCOURT_NAME_2);
		courtDAO.update(court);
		Court courtUpdated = courtDAO.findById(court.getId());
		assertEquals(SPORTCOURT_NAME_2, courtUpdated.getName());

	}

	@Test
	public void updateCenter() throws Exception {
		Court court = createSportCourtReturnRef().getValue();
		assertEquals(centerRefLaCantera, court.getCenterRef());
		court.setCenterRef(centerRefPabellonEuropa);
		courtDAO.update(court);
		assertEquals(centerRefPabellonEuropa, court.getCenterRef());
	}

	@Test
	public void updateSports() throws Exception {
		Court court = createSportCourtReturnRef().getValue();
		assertEquals(2, court.getSports().size());
		court.getSports().remove(0);
		courtDAO.update(court);
		assertEquals(1, court.getSports().size());
	}

	@Test
	public void remove() throws Exception {
		Court court = createSportCourtReturnRef().getValue();
		assertEquals(court, courtDAO.findById(court.getId()));
		courtDAO.remove(court);
		assertEquals(null, courtDAO.findById(court.getId()));
	}

	@Test
	public void findAllSportCourt() throws Exception {
		createSportCourt();
		createSportCourt();
		assertEquals(2, courtDAO.findAll().size());
	}

	@Test
	public void findSportCourtById() throws Exception {
		Key<Court> sportCourtKey = createSportCourt();
		Court court = courtDAO.findById(sportCourtKey.getId());
		assertEquals(sportCourtKey.getId(), (long) court.getId());
	}

	@Test
	public void findSportCourtByCenterRef() throws Exception {
		createSportCourt();
		assertEquals(1, courtDAO.findBySportCenter(centerRefLaCantera.get().getId()).size());
		assertEquals(0, courtDAO.findBySportCenter(centerRefPabellonEuropa.get().getId()).size());
	}

	@Test
	public void findSportCourtBySport() throws Exception {
		createSportCourt();
		createSportCourt();
		List<Court> courtList = courtDAO.findBySport(sportRefBasket.get().getId());
		assertEquals(2, courtList.size());

	}


	private Key<Court> createSportCourt() throws Exception {
		Court court = new Court();
		court.setName(SPORTCOURT_NAME_1);
		court.setCenterRef(centerRefLaCantera);
		List<Ref<Sport>> list = new ArrayList<>();
		list.add(sportRefFutbol);
		court.getSports().add(sportRefBasket);
		court.getSports().add(sportRefFutbol);
		return courtDAO.create(court);
	}


	private Ref<Court> createSportCourtReturnRef() throws Exception {
		Court court = new Court();
		court.setName(SPORTCOURT_NAME_1);
		court.setCenterRef(centerRefLaCantera);
		List<Ref<Sport>> list = new ArrayList<>();
		list.add(sportRefFutbol);
		court.getSports().add(sportRefBasket);
		court.getSports().add(sportRefFutbol);
		return courtDAO.createReturnRef(court);
	}
}