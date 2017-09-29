package com.adiaz.daos;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sanction;
import com.adiaz.entities.Team;
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

import static com.googlecode.objectify.ObjectifyService.ofy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by toni on 28/09/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class SanctionsDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired SanctionsDAO sanctionsDAO;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Sanction.class);
		ObjectifyService.register(Competition.class);
		ObjectifyService.register(Team.class);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		Sanction sanction = new Sanction();
		sanction.setPoints(3);
		Key<Sanction> sanctionKey = sanctionsDAO.create(sanction);
		Sanction sanctionFromDatastore = ofy().load().type(Sanction.class).id(sanctionKey.getId()).now();
		assertTrue(sanctionFromDatastore!=null);
	}

	@Test
	public void update() throws Exception {
		Sanction sanction = createEntity();
		sanction.setPoints(33);
		sanctionsDAO.update(sanction);
		Sanction sanctionFromDatastore = ofy().load().type(Sanction.class).id(sanction.getId()).now();
		assertEquals(33, sanctionFromDatastore.getPoints().intValue());
	}

	@Test
	public void remove() throws Exception {
		Sanction sanction = createEntity();
		sanctionsDAO.remove(sanction);
		Sanction sanctionFormDatastore = ofy().load().type(Sanction.class).id(sanction.getId()).now();
		assertNull(sanctionFormDatastore);
	}

	@Test
	public void findById() throws Exception {
		Sanction sanction = createEntity();
		Sanction sanctionFromDatastore = sanctionsDAO.findById(sanction.getId());
		assertEquals(sanction, sanctionFromDatastore);
	}

	@Test
	public void findByCompetitionId() throws Exception {
		Sanction sanction = createEntity();
		sanction.getRefs();
		List<Sanction> list = sanctionsDAO.findByCompetitionId(sanction.getCompetition().getId());
		assertEquals(1, list.size());
	}

	@Test
	public void findByCompetitionIdAndTeamId() throws Exception {
		Sanction sanction = createEntity();
		sanction.getRefs();
		List<Sanction> list = sanctionsDAO.findByCompetitionIdAndTeamId(sanction.getCompetition().getId(), sanction.getTeam().getId());
		assertEquals(1, list.size());
	}

	private Sanction createEntity() {
		Team team = new Team();
		Key<Team> teamKey = ofy().save().entity(team).now();
		Competition competition = new Competition();
		Key<Competition> competitionKey = ofy().save().entity(competition).now();
		Sanction sanction = new Sanction();
		sanction.setPoints(3);
		sanction.setCompetitionRef(Ref.create(competitionKey));
		sanction.setTeamRef(Ref.create(teamKey));
		Key<Sanction> now = ofy().save().entity(sanction).now();
		return Ref.create(now).get();
	}


}