package com.adiaz.daos;

import com.adiaz.entities.Sport;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Created by toni on 11/07/2017. */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class SportsDAOImplTest {

    private static final String SPORT_BALONCESTO = "Baloncesto";
    private static final String SPORT_FUTBOL = "Futbol";
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    private SportsDAO sportsDAO;


    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(Sport.class);
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void create() throws Exception {
        assertEquals(0, sportsDAO.findAll().size());
        Key<Sport> key = createSport();
        assertEquals(1, sportsDAO.findAll().size());
        Sport sport = sportsDAO.findById(key.getId());
        assertEquals(key.getId(), (long)sport.getId());
        assertEquals(SPORT_BALONCESTO, sport.getName());
    }

    @Test
    public void update() throws Exception {
        Key<Sport> key = createSport();
        Sport sport = sportsDAO.findById(key.getId());
        sport.setName(SPORT_FUTBOL);
        sportsDAO.update(sport);
        sport = sportsDAO.findById(key.getId());
        assertEquals(SPORT_FUTBOL, sport.getName());
    }

    @Test
    public void remove() throws Exception {
        Key<Sport> key = createSport();
        Sport sport = sportsDAO.findById(key.getId());
        sportsDAO.remove(sport);
        sport = sportsDAO.findById(key.getId());
        assertTrue(sport==null);
    }

    @Test
    public void findAllSports() throws Exception {
        createSport();
        createSport();
        assertEquals(2, sportsDAO.findAll().size());
    }

    @Test
    public void findSportById() throws Exception {
        Key<Sport> key = createSport();
        assertEquals(key.getId(), (long)sportsDAO.findById(key.getId()).getId());
    }

    private Key<Sport> createSport() throws Exception {
        Sport sport = new Sport();
        sport.setName(SPORT_BALONCESTO);
        return sportsDAO.create(sport);
    }
}