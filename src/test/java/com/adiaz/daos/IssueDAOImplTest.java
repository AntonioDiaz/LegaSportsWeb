package com.adiaz.daos;

import com.adiaz.entities.*;
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

import static org.junit.Assert.*;

/**
 * Created by toni on 14/09/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class IssueDAOImplTest {

	public static final String MY_CLIENT_INSTANCE_A = "MY_CLIENT_INSTANCE_A";
	private static final String MY_CLIENT_INSTANCE_B = "MY_CLIENT_INSTANCE_B";
	public static final String COPA_PRIMAVERA = "COPA_PRIMAVERA";
	public static final String LEGANES = "LEGANES";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	IssueDAO issueDAO;

	@Autowired
	MatchesDAO matchesDAO;

	@Autowired
	CompetitionsDAO competitionsDAO;

	@Autowired
	TownDAO townDAO;

	private Ref<Competition> competitionRef;
	private Ref<Town> refTownLeganes;


	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Match.class);
		ObjectifyService.register(Competition.class);
		ObjectifyService.register(Town.class);
		ObjectifyService.register(Issue.class);


		Competition competition = new Competition();
		competition.setName(COPA_PRIMAVERA);
		Key<Competition> key = competitionsDAO.create(competition);
		competitionRef = Ref.create(key);

		/*town: leganes*/
		Town town = new Town();
		town.setName(LEGANES);
		Key<Town> townKey = townDAO.create(town);
		refTownLeganes = Ref.create(townKey);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}


	@Test
	public void create() throws Exception {
		createIssue();
		Issue issueSaved = issueDAO.findAll().get(0);
		assertEquals(MY_CLIENT_INSTANCE_A, issueSaved.getClientInstanceId());
		assertEquals(competitionRef, issueSaved.getCompetitionRef());
	}

	@Test
	public void update() throws Exception {
		createIssue();
		Issue issueSaved = issueDAO.findAll().get(0);
		assertEquals(MY_CLIENT_INSTANCE_A, issueSaved.getClientInstanceId());
		issueSaved.setClientInstanceId(MY_CLIENT_INSTANCE_B);
		issueDAO.update(issueSaved);
		assertEquals(MY_CLIENT_INSTANCE_B, issueSaved.getClientInstanceId());
		assertEquals(1, issueDAO.findAll().size());
	}

	@Test
	public void remove() throws Exception {
		Long issueId = createIssue();
		assertEquals(1, issueDAO.findAll().size());
		Issue issue = new Issue();
		issue.setId(issueId);
		issueDAO.remove(issue);
		assertEquals(0, issueDAO.findAll().size());
	}

	@Test
	public void findByCompetition() throws Exception {
		createIssue();
		createIssue();
		Long issueId = createIssue();
		List<Issue> byCompetition = issueDAO.findByCompetition(competitionRef.getKey().getId());
		assertEquals(3, byCompetition.size());
		Issue issue = issueDAO.findById(issueId);
		issue.setCompetitionRef(null);
		issueDAO.update(issue);
		byCompetition = issueDAO.findByCompetition(competitionRef.getKey().getId());
		assertEquals(2, byCompetition.size());

	}

	@Test
	public void findByTown() throws Exception {
		createIssue();
		createIssue();
		createIssue();
		Long idIssue = createIssue();
		//List<Issue> byTown = issueDAO.findByTown(refTownLeganes.getKey().getId());
		List<Issue> byTown = issueDAO.findAll();
		assertEquals(4, issueDAO.findByTown(refTownLeganes.get().getId()).size());
		Issue issue = issueDAO.findById(idIssue);
		issue.setTownRef(null);
		issueDAO.update(issue);
		assertEquals(3, issueDAO.findByTown(refTownLeganes.get().getId()).size());

	}

	@Test
	public void findAll() throws Exception {
		createIssue();
		createIssue();
		createIssue();
		List<Issue> issues = issueDAO.findAll();
		assertEquals(3, issues.size());

	}

	@Test
	public void findById() throws Exception {
		createIssue();
		Long idIssue = createIssue();
		Issue issue = issueDAO.findById(idIssue);
		assertNotNull(issue);
		assertEquals(idIssue, issue.getId());

	}

	private Long createIssue() throws Exception {
		Issue issue = new Issue();
		issue.setClientInstanceId(MY_CLIENT_INSTANCE_A);
		issue.setCompetitionRef(competitionRef);
		issue.setTownRef(refTownLeganes);
		Key<Issue> issueKey = issueDAO.create(issue);
		return issueKey.getId();
	}

}