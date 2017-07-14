package com.adiaz.daos;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCourt;
import com.adiaz.entities.Sport;
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

/** Created by toni on 11/07/2017. */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class SportCourtDAOImplTest {

    private static final String SPORTCOURT_NAME_1 = "PISTA CENTRAL";
    private static final String SPORTCOURT_NAME_2 = "PISTA SECUNDARIA";
    public static final String LA_CANTERA = "La cantera";
    private static final String PABELLON_EUROPA = "Pabellon Europa";
    public static final String BASKET = "Baloncesto";
    public static final String FUTBOL = "Futbol";

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    SportCourtDAO sportCourtDAO;
    @Autowired
    SportCenterDAO sportCenterDAO;
    @Autowired
    SportsDAO sportsDAO;

    private Ref<SportCenter> centerRefLaCantera;
    private Ref<SportCenter> centerRefPabellonEuropa;
    private Ref<Sport> sportRefFutbol;
    private Ref<Sport> sportRefBasket;



    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(SportCourt.class);
        ObjectifyService.register(SportCenter.class);
        ObjectifyService.register(Sport.class);
        /* create center 01*/
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName(LA_CANTERA);
        Key<SportCenter> sportCenterKey = sportCenterDAO.create(sportCenter);
        centerRefLaCantera = Ref.create(sportCenterKey);
        /* create center 02*/
        sportCenter = new SportCenter();
        sportCenter.setName(PABELLON_EUROPA);
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
        Assert.assertEquals(0, sportCourtDAO.findAllSportCourt().size());
        Key<SportCourt> key = createSportCourt();
        Assert.assertEquals(1, sportCourtDAO.findAllSportCourt().size());
        SportCourt sportCourt = sportCourtDAO.findSportCourt(key.getId());
        Assert.assertEquals(centerRefLaCantera, sportCourt.getCenter());
        Assert.assertEquals(LA_CANTERA, centerRefLaCantera.getValue().getName());
        Assert.assertEquals(2, sportCourt.getSports().size());
        Assert.assertEquals(BASKET, sportCourt.getSports().get(0).getValue().getName());
        Assert.assertEquals(FUTBOL, sportCourt.getSports().get(1).getValue().getName());
    }

    @Test
    public void createReturnRef() throws Exception {
        Assert.assertEquals(0, sportCourtDAO.findAllSportCourt().size());
        Ref<SportCourt> sportCourtReturnRef = createSportCourtReturnRef();
        Assert.assertEquals(1, sportCourtDAO.findAllSportCourt().size());
        Assert.assertEquals(SPORTCOURT_NAME_1, sportCourtReturnRef.getValue().getName());
    }

    @Test
    public void updateName() throws Exception {
        Ref<SportCourt> ref = createSportCourtReturnRef();
        SportCourt sportCourt = ref.getValue();
        sportCourt.setName(SPORTCOURT_NAME_2);
        sportCourtDAO.update(sportCourt);
        SportCourt sportCourtUpdated = sportCourtDAO.findSportCourt(sportCourt.getId());
        Assert.assertEquals(SPORTCOURT_NAME_2, sportCourtUpdated.getName());

    }

    @Test
    public void updateCenter() throws Exception {
        SportCourt sportCourt = createSportCourtReturnRef().getValue();
        Assert.assertEquals(centerRefLaCantera, sportCourt.getCenter());
        sportCourt.setCenter(centerRefPabellonEuropa);
        sportCourtDAO.update(sportCourt);
        Assert.assertEquals(centerRefPabellonEuropa, sportCourt.getCenter());
    }

    @Test
    public void updateSports() throws Exception {
        SportCourt sportCourt = createSportCourtReturnRef().getValue();
        Assert.assertEquals(2, sportCourt.getSports().size());
        sportCourt.getSports().remove(0);
        sportCourtDAO.update(sportCourt);
        Assert.assertEquals(1, sportCourt.getSports().size());
    }

    @Test
    public void remove() throws Exception {
        SportCourt sportCourt = createSportCourtReturnRef().getValue();
        Assert.assertEquals(sportCourt, sportCourtDAO.findSportCourt(sportCourt.getId()));
        sportCourtDAO.remove(sportCourt);
        Assert.assertEquals(null, sportCourtDAO.findSportCourt(sportCourt.getId()));
    }

    @Test
    public void findAllSportCourt() throws Exception {
        createSportCourt();
        createSportCourt();
        Assert.assertEquals(2, sportCourtDAO.findAllSportCourt().size());
    }

    @Test
    public void findSportCourtById() throws Exception {
        Key<SportCourt> sportCourtKey = createSportCourt();
        SportCourt sportCourt = sportCourtDAO.findSportCourt(sportCourtKey.getId());
        Assert.assertEquals(sportCourtKey.getId(), (long)sportCourt.getId());
    }

    @Test
    public void findSportCourtByCenterRef() throws Exception {
        createSportCourt();
        Assert.assertEquals(1, sportCourtDAO.findSportCourt(centerRefLaCantera).size());
        Assert.assertEquals(0, sportCourtDAO.findSportCourt(centerRefPabellonEuropa).size());
    }


    private Key<SportCourt> createSportCourt() throws Exception {
        SportCourt sportCourt = new SportCourt();
        sportCourt.setName(SPORTCOURT_NAME_1);
        sportCourt.setCenter(centerRefLaCantera);
        List<Ref<Sport>> list = new ArrayList<>();
        list.add(sportRefFutbol);
        sportCourt.getSports().add(sportRefBasket);
        sportCourt.getSports().add(sportRefFutbol);
        return sportCourtDAO.create(sportCourt);
    }


    private Ref<SportCourt> createSportCourtReturnRef() throws Exception {
        SportCourt sportCourt = new SportCourt();
        sportCourt.setName(SPORTCOURT_NAME_1);
        sportCourt.setCenter(centerRefLaCantera);
        List<Ref<Sport>> list = new ArrayList<>();
        list.add(sportRefFutbol);
        sportCourt.getSports().add(sportRefBasket);
        sportCourt.getSports().add(sportRefFutbol);
        return sportCourtDAO.createReturnRef(sportCourt);
    }
}