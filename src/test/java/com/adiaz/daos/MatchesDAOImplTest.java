package com.adiaz.daos;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Match;
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

/**
 * Created by toni on 11/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class MatchesDAOImplTest {

	private static final String ATLETICO_MADRID = "ATLETICO_MADRID";
	public static final String LEGANES = "LEGANES";
	public static final String COPA_PRIMAVERA = "COPA PRIMAVERA";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	@Autowired
	MatchesDAO matchesDAO;
	@Autowired
	CompetitionsDAO competitionsDAO;
	private Ref<Competition> competitionRef;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Match.class);
		ObjectifyService.register(Competition.class);
		Competition competition = new Competition();
		competition.setName(COPA_PRIMAVERA);
		Key<Competition> key = competitionsDAO.create(competition);
		competitionRef = Ref.create(key);
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
		Key<Match> key = createMatch();
		Match match = Ref.create(key).getValue();
		match.setTeamLocal(LEGANES);
		matchesDAO.update(match);
		Assert.assertEquals(LEGANES, matchesDAO.findById(match.getId()).getTeamLocal());

	}

	@Test
	public void remove() throws Exception {
		Key<Match> key = createMatch();
		Match match = Ref.create(key).getValue();
		matchesDAO.remove(match);
		Assert.assertEquals(0, matchesDAO.findAll().size());
	}

	@Test
	public void findByCompetition() throws Exception {
		createMatch();
		createMatch();
		Assert.assertEquals(2, matchesDAO.findByCompetition(competitionRef.getKey().getId()).size());
	}

	@Test
	public void findAll() throws Exception {
		createMatch();
		createMatch();
		Assert.assertEquals(2, matchesDAO.findAll().size());
	}

	@Test
	public void findById() throws Exception {
		Key<Match> key = createMatch();
		Match match = Ref.create(key).getValue();
		Assert.assertEquals(match.getId(), matchesDAO.findById(match.getId()).getId());
	}

	private Key<Match> createMatch() throws Exception {
		Match match = new Match();
		match.setTeamLocal(ATLETICO_MADRID);
		match.setCompetitionRef(competitionRef);
		match.setWorkingCopy(false);
		return matchesDAO.create(match);
	}

}