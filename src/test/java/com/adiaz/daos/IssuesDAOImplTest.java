package com.adiaz.daos;

import com.adiaz.entities.*;
import com.adiaz.utils.LocalSportsUtils;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by toni on 14/09/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class IssuesDAOImplTest {

	public static final String MY_CLIENT_INSTANCE_A = "MY_CLIENT_INSTANCE_A";
	private static final String MY_CLIENT_INSTANCE_B = "MY_CLIENT_INSTANCE_B";
	public static final String COPA_PRIMAVERA = "COPA_PRIMAVERA";
	public static final String LEGANES = "LEGANES";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	IssuesDAO issuesDAO;

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
		Issue issueSaved = issuesDAO.findAll().get(0);
		assertEquals(MY_CLIENT_INSTANCE_A, issueSaved.getClientId());
		assertEquals(competitionRef, issueSaved.getCompetitionRef());
	}

	@Test
	public void update() throws Exception {
		createIssue();
		Issue issueSaved = issuesDAO.findAll().get(0);
		assertEquals(MY_CLIENT_INSTANCE_A, issueSaved.getClientId());
		issueSaved.setClientId(MY_CLIENT_INSTANCE_B);
		issuesDAO.update(issueSaved);
		assertEquals(MY_CLIENT_INSTANCE_B, issueSaved.getClientId());
		assertEquals(1, issuesDAO.findAll().size());
	}

	@Test
	public void remove() throws Exception {
		Long issueId = createIssue();
		assertEquals(1, issuesDAO.findAll().size());
		Issue issue = new Issue();
		issue.setId(issueId);
		issuesDAO.remove(issue);
		assertEquals(0, issuesDAO.findAll().size());
	}

	@Test
	public void findByCompetition() throws Exception {
		createIssue();
		createIssue();
		Long issueId = createIssue();
		List<Issue> byCompetition = issuesDAO.findByCompetition(competitionRef.getKey().getId());
		assertEquals(3, byCompetition.size());
		Issue issue = issuesDAO.findById(issueId);
		issue.setCompetitionRef(null);
		issuesDAO.update(issue);
		byCompetition = issuesDAO.findByCompetition(competitionRef.getKey().getId());
		assertEquals(2, byCompetition.size());

	}

	@Test
	public void findByTown() throws Exception {
		createIssue();
		createIssue();
		createIssue();
		Long idIssue = createIssue();
		//List<Issue> byTown = issuesDAO.findByTown(refTownLeganes.getKey().getId());
		List<Issue> byTown = issuesDAO.findAll();
		assertEquals(4, issuesDAO.findByTown(refTownLeganes.get().getId()).size());
		Issue issue = issuesDAO.findById(idIssue);
		issue.setTownRef(null);
		issuesDAO.update(issue);
		assertEquals(3, issuesDAO.findByTown(refTownLeganes.get().getId()).size());

	}

	@Test
	public void findAll() throws Exception {
		createIssue();
		createIssue();
		createIssue();
		List<Issue> issues = issuesDAO.findAll();
		assertEquals(3, issues.size());

	}

	@Test
	public void findById() throws Exception {
		createIssue();
		Long idIssue = createIssue();
		Issue issue = issuesDAO.findById(idIssue);
		assertNotNull(issue);
		assertEquals(idIssue, issue.getId());
	}

	@Test
	public void findByClientIdInPeriod() throws Exception {
		createIssue();
		createIssue();
		Date dateFrom = LocalSportsUtils.calculateLastMidnight();
		Date dateTo = LocalSportsUtils.calculateNextMidnigth();
		List<Issue> byClientIdInPeriod = issuesDAO.findByClientIdInPeriod(MY_CLIENT_INSTANCE_A, dateFrom, dateTo);
		assertEquals(2, byClientIdInPeriod.size());
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		dateFrom = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		dateTo = calendar.getTime();
		byClientIdInPeriod = issuesDAO.findByClientIdInPeriod(MY_CLIENT_INSTANCE_A, dateFrom, dateTo);
		assertEquals(0, byClientIdInPeriod.size());
	}

	@Test
	public void findInPeriod() throws Exception {
		createIssue();
		createIssue();
		Date dateFrom = LocalSportsUtils.calculateLastMidnight();
		Date dateTo = LocalSportsUtils.calculateNextMidnigth();
		List<Issue> inPeriod = issuesDAO.findInPeriod(dateFrom, dateTo);
		assertEquals(2, inPeriod.size());
	}

	private Long createIssue() throws Exception {
		Issue issue = new Issue();
		issue.setClientId(MY_CLIENT_INSTANCE_A);
		issue.setCompetitionRef(competitionRef);
		issue.setTownRef(refTownLeganes);
		issue.setDateSent(new Date());
		Key<Issue> issueKey = issuesDAO.create(issue);
		return issueKey.getId();
	}



}