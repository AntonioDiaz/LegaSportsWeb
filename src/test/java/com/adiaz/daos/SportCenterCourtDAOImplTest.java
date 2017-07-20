package com.adiaz.daos;

import com.adiaz.entities.SportCenter;
import com.adiaz.entities.SportCenterCourt;
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
public class SportCenterCourtDAOImplTest {

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
        ObjectifyService.register(SportCenterCourt.class);
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
        Key<SportCenterCourt> key = createSportCourt();
        Assert.assertEquals(1, sportCourtDAO.findAllSportCourt().size());
        SportCenterCourt sportCenterCourt = sportCourtDAO.findSportCourt(key.getId());
        Assert.assertEquals(centerRefLaCantera, sportCenterCourt.getSportCenterRef());
        Assert.assertEquals(LA_CANTERA, centerRefLaCantera.getValue().getName());
        Assert.assertEquals(2, sportCenterCourt.getSports().size());
        Assert.assertEquals(BASKET, sportCenterCourt.getSports().get(0).getValue().getName());
        Assert.assertEquals(FUTBOL, sportCenterCourt.getSports().get(1).getValue().getName());
    }

    @Test
    public void createReturnRef() throws Exception {
        Assert.assertEquals(0, sportCourtDAO.findAllSportCourt().size());
        Ref<SportCenterCourt> sportCourtReturnRef = createSportCourtReturnRef();
        Assert.assertEquals(1, sportCourtDAO.findAllSportCourt().size());
        Assert.assertEquals(SPORTCOURT_NAME_1, sportCourtReturnRef.getValue().getName());
    }

    @Test
    public void updateName() throws Exception {
        Ref<SportCenterCourt> ref = createSportCourtReturnRef();
        SportCenterCourt sportCenterCourt = ref.getValue();
        sportCenterCourt.setName(SPORTCOURT_NAME_2);
        sportCourtDAO.update(sportCenterCourt);
        SportCenterCourt sportCenterCourtUpdated = sportCourtDAO.findSportCourt(sportCenterCourt.getId());
        Assert.assertEquals(SPORTCOURT_NAME_2, sportCenterCourtUpdated.getName());

    }

    @Test
    public void updateCenter() throws Exception {
        SportCenterCourt sportCenterCourt = createSportCourtReturnRef().getValue();
        Assert.assertEquals(centerRefLaCantera, sportCenterCourt.getSportCenterRef());
        sportCenterCourt.setSportCenterRef(centerRefPabellonEuropa);
        sportCourtDAO.update(sportCenterCourt);
        Assert.assertEquals(centerRefPabellonEuropa, sportCenterCourt.getSportCenterRef());
    }

    @Test
    public void updateSports() throws Exception {
        SportCenterCourt sportCenterCourt = createSportCourtReturnRef().getValue();
        Assert.assertEquals(2, sportCenterCourt.getSports().size());
        sportCenterCourt.getSports().remove(0);
        sportCourtDAO.update(sportCenterCourt);
        Assert.assertEquals(1, sportCenterCourt.getSports().size());
    }

    @Test
    public void remove() throws Exception {
        SportCenterCourt sportCenterCourt = createSportCourtReturnRef().getValue();
        Assert.assertEquals(sportCenterCourt, sportCourtDAO.findSportCourt(sportCenterCourt.getId()));
        sportCourtDAO.remove(sportCenterCourt);
        Assert.assertEquals(null, sportCourtDAO.findSportCourt(sportCenterCourt.getId()));
    }

    @Test
    public void findAllSportCourt() throws Exception {
        createSportCourt();
        createSportCourt();
        Assert.assertEquals(2, sportCourtDAO.findAllSportCourt().size());
    }

    @Test
    public void findSportCourtById() throws Exception {
        Key<SportCenterCourt> sportCourtKey = createSportCourt();
        SportCenterCourt sportCenterCourt = sportCourtDAO.findSportCourt(sportCourtKey.getId());
        Assert.assertEquals(sportCourtKey.getId(), (long) sportCenterCourt.getId());
    }

    @Test
    public void findSportCourtByCenterRef() throws Exception {
        createSportCourt();
        Assert.assertEquals(1, sportCourtDAO.findSportCourt(centerRefLaCantera).size());
        Assert.assertEquals(0, sportCourtDAO.findSportCourt(centerRefPabellonEuropa).size());
    }


    private Key<SportCenterCourt> createSportCourt() throws Exception {
        SportCenterCourt sportCenterCourt = new SportCenterCourt();
        sportCenterCourt.setName(SPORTCOURT_NAME_1);
        sportCenterCourt.setSportCenterRef(centerRefLaCantera);
        List<Ref<Sport>> list = new ArrayList<>();
        list.add(sportRefFutbol);
        sportCenterCourt.getSports().add(sportRefBasket);
        sportCenterCourt.getSports().add(sportRefFutbol);
        return sportCourtDAO.create(sportCenterCourt);
    }


    private Ref<SportCenterCourt> createSportCourtReturnRef() throws Exception {
        SportCenterCourt sportCenterCourt = new SportCenterCourt();
        sportCenterCourt.setName(SPORTCOURT_NAME_1);
        sportCenterCourt.setSportCenterRef(centerRefLaCantera);
        List<Ref<Sport>> list = new ArrayList<>();
        list.add(sportRefFutbol);
        sportCenterCourt.getSports().add(sportRefBasket);
        sportCenterCourt.getSports().add(sportRefFutbol);
        return sportCourtDAO.createReturnRef(sportCenterCourt);
    }
}