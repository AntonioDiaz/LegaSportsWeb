package com.adiaz.daos;

import com.adiaz.entities.Category;
import com.adiaz.entities.Competition;
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

/** Created by toni on 11/07/2017. */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class CompetitionsDAOImplTest {
    private static final String COPA_PRIMAVERA = "COPA_PRIMAVERA";
    private static final String COPA_LIGA = "COPA_LIGA";
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    CompetitionsDAO competitionsDAO;
    @Autowired
    CategoriesDAO categoriesDAO;
    @Autowired
    SportsDAO sportsDAO;
    
    private Ref<Category> refCategory;
    private Ref<Sport> refSportBasket;
    private Ref<Sport> refSportFutbol;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(Competition.class);
        ObjectifyService.register(Category.class);
        ObjectifyService.register(Sport.class);
        Category category = new Category();
        category.setName("Cadete");
        Key<Category> categoryKey = categoriesDAO.create(category);
        refCategory = Ref.create(categoryKey);
        Sport sport = new Sport();
        sport.setName("Basket");
        Key<Sport> sportKey = sportsDAO.create(sport);
        refSportBasket = Ref.create(sportKey);
        sport = new Sport();
        sport.setName("Futbol");
        sportKey = sportsDAO.create(sport);
        refSportFutbol = Ref.create(sportKey);
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void create() throws Exception {
        Key<Competition> key = createCompetition(COPA_PRIMAVERA);
        Competition competition = Ref.create(key).getValue();
        Assert.assertEquals(competition, competitionsDAO.findCompetitionsById(competition.getId()));
    }

    @Test
    public void updateName() throws Exception {
        Key<Competition> key = createCompetition(COPA_PRIMAVERA);
        Competition competition = Ref.create(key).getValue();
        competition.setName(COPA_LIGA);
        competitionsDAO.update(competition);
        competition = competitionsDAO.findCompetitionsById(competition.getId());
        Assert.assertEquals(COPA_LIGA, competition.getName());
    }

    @Test
    public void updateSport() throws Exception {
        Key<Competition> key = createCompetition(COPA_PRIMAVERA);
        Competition competition = Ref.create(key).getValue();
        competition.setSport(refSportFutbol);
        competitionsDAO.update(competition);
        competition = competitionsDAO.findCompetitionsById(competition.getId());
        Assert.assertEquals(refSportFutbol, competition.getSport());
        competition.getRefs();
        Assert.assertEquals("Futbol", competition.getSportEntity().getName());
    }

    @Test
    public void remove() throws Exception {
        Key<Competition> key = createCompetition(COPA_PRIMAVERA);
        Competition competition = Ref.create(key).getValue();
        competitionsDAO.remove(competition);
        Assert.assertEquals(0, competitionsDAO.findCompetitions().size());
    }

    @Test
    public void findCompetitions() throws Exception {
        createCompetition(COPA_PRIMAVERA);
        createCompetition(COPA_LIGA);
        Assert.assertEquals(2, competitionsDAO.findCompetitions().size());
    }

    @Test
    public void findCompetitionsBySport() throws Exception {
        createCompetition(COPA_PRIMAVERA);
        createCompetition(COPA_LIGA);
        Assert.assertEquals(2, competitionsDAO.findCompetitionsBySport(refSportFutbol.getValue()).size());
    }

    @Test
    public void findCompetitionsBySportAndCategory() throws Exception {
        Key<Competition> keyCompetition01 = createCompetition(COPA_PRIMAVERA);
        Key<Competition> keyCompetition02 = createCompetition(COPA_LIGA);

        long idCategory = refCategory.getKey().getId();
        long idSport = refSportBasket.getKey().getId();
        Assert.assertEquals(2, competitionsDAO.findCompetitions(idSport, idCategory).size());
    }

    @Test
    public void findCompetitionsById() throws Exception {
        Key<Competition> key = createCompetition(COPA_PRIMAVERA);
        Competition competition = Ref.create(key).getValue();
        Assert.assertEquals(competition, competitionsDAO.findCompetitionsById(key.getId()));
    }

    private Key<Competition> createCompetition(String competitionName) throws Exception {
        Competition competition = new Competition();
        competition.setName(competitionName);
        competition.setSport(refSportBasket);
        competition.setCategory(refCategory);
        return competitionsDAO.create(competition);
    }


}