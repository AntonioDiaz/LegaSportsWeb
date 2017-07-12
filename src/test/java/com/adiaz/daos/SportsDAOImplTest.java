package com.adiaz.daos;

import com.adiaz.entities.SportVO;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
        ObjectifyService.register(SportVO.class);
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void create() throws Exception {
        Assert.assertEquals(0, sportsDAO.findAllSports().size());
        Key<SportVO> key = createSport();
        Assert.assertEquals(1, sportsDAO.findAllSports().size());
        SportVO sport = sportsDAO.findSportById(key.getId());
        Assert.assertEquals(key.getId(), (long)sport.getId());
        Assert.assertEquals(SPORT_BALONCESTO, sport.getName());
    }

    @Test
    public void update() throws Exception {
        Key<SportVO> key = createSport();
        SportVO sport = sportsDAO.findSportById(key.getId());
        sport.setName(SPORT_FUTBOL);
        sportsDAO.update(sport);
        sport = sportsDAO.findSportById(key.getId());
        Assert.assertEquals(SPORT_FUTBOL, sport.getName());
    }

    @Test
    public void remove() throws Exception {
        Key<SportVO> key = createSport();
        SportVO sport = sportsDAO.findSportById(key.getId());
        sportsDAO.remove(sport);
        sport = sportsDAO.findSportById(key.getId());
        Assert.assertTrue(sport==null);
    }

    @Test
    public void findAllSports() throws Exception {
        createSport();
        createSport();
        Assert.assertEquals(2, sportsDAO.findAllSports().size());
    }

    @Test
    public void findSportById() throws Exception {
        Key<SportVO> key = createSport();
        Assert.assertEquals(key.getId(), (long)sportsDAO.findSportById(key.getId()).getId());
    }

    private Key<SportVO> createSport() throws Exception {
        SportVO sport = new SportVO();
        sport.setName(SPORT_BALONCESTO);
        return sportsDAO.create(sport);
    }
}