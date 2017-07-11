package com.adiaz.daos;

import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
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

/** Created by toni on 11/07/2017. */
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MatchesDAOImplTest {

    private static final String ATLETICO_MADRID = "ATLETICO_MADRID";
    public static final String LEGANES = "LEGANES";
    public static final String COPA_PRIMAVERA = "COPA PRIMAVERA";
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    @Autowired
    MatchesDAO matchesDAO;
    @Autowired
    CompetitionsDAO competitionsDAO;
    private Ref<CompetitionsVO> competitionsVORef;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(MatchesVO.class);
        ObjectifyService.register(CompetitionsVO.class);
        CompetitionsVO competitionsVO = new CompetitionsVO();
        competitionsVO.setName(COPA_PRIMAVERA);
        Key<CompetitionsVO> key = competitionsDAO.create(competitionsVO);
        competitionsVORef = Ref.create(key);
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void create() throws Exception {
        createMatch();
        Assert.assertEquals(1, matchesDAO.findAll().size());
    }

    @Test
    public void update() throws Exception {
        Key<MatchesVO> key = createMatch();
        MatchesVO match = Ref.create(key).getValue();
        match.setTeamLocal(LEGANES);
        matchesDAO.update(match);
        Assert.assertEquals(LEGANES, matchesDAO.findById(match.getId()).getTeamLocal());

    }

    @Test
    public void remove() throws Exception {
        Key<MatchesVO> key = createMatch();
        MatchesVO match = Ref.create(key).getValue();
        matchesDAO.remove(match);
        Assert.assertEquals(0, matchesDAO.findAll().size());
    }

    @Test
    public void findByCompetition() throws Exception {
        createMatch();
        createMatch();
        Assert.assertEquals(2, matchesDAO.findByCompetition(competitionsVORef.getKey().getId()).size());
    }

    @Test
    public void findAll() throws Exception {
        createMatch();
        createMatch();
        Assert.assertEquals(2, matchesDAO.findAll().size());
    }

    @Test
    public void findById() throws Exception {
        Key<MatchesVO> key = createMatch();
        MatchesVO match = Ref.create(key).getValue();
        Assert.assertEquals(match.getId(), matchesDAO.findById(match.getId()).getId());
    }

    private Key<MatchesVO> createMatch() throws Exception {
        MatchesVO match = new MatchesVO();
        match.setTeamLocal(ATLETICO_MADRID);
        match.setCompetitionRef(competitionsVORef);
        return matchesDAO.create(match);
    }

}