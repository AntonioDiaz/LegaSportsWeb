package com.adiaz.daos;

import com.adiaz.entities.ClassificationEntry;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Team;
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

import static org.junit.Assert.assertEquals;

/** Created by toni on 11/07/2017. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class ClassificationEntriesDAOImplTest {

	private static final String ATLETICO_MADRID = "ATLETICO_MADRID";
    private static final String LEGANES = "LEGANES";
    public static final String COPA_DE_PRIMAVERA = "COPA DE PRIMAVERA";
	public static final String COPA_LIGA = "COPA_LIGA";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    ClassificationEntriesDAO classificationEntriesDAO;
    @Autowired
    CompetitionsDAO competitionsDAO;
    @Autowired
	TeamDAO teamDAO;


    private Ref<Competition> copaPrimaveraRef;
    private Ref<Competition> copaLigaRef;
    private Ref<Team> teamRefLeganes;
    private Ref<Team> teamRefAtleti;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(ClassificationEntry.class);
        ObjectifyService.register(Competition.class);
        ObjectifyService.register(Team.class);

        /*create competition: copa de primavera*/
        Competition competitionCopa = new Competition();
        competitionCopa.setName(COPA_DE_PRIMAVERA);
        Key<Competition> competitionKey = competitionsDAO.create(competitionCopa);
        copaPrimaveraRef = Ref.create(competitionKey);

        /*create competition: copa de liga*/
		Competition ligaCompetition = new Competition();
		ligaCompetition.setName(COPA_LIGA);
		Key<Competition> copaLigaKey = competitionsDAO.create(ligaCompetition);
		copaLigaRef = Ref.create(copaLigaKey);
		/*create team: leganes*/
		Team team = new Team();
        team.setName(LEGANES);
		Key<Team> teamKeyLeganes = teamDAO.create(team);
		teamRefLeganes = Ref.create(teamKeyLeganes);
		/*create team: Atletico de madrid.*/
		team = new Team();
		team.setName(ATLETICO_MADRID);
		Key<Team> teamKey = teamDAO.create(team);
		teamRefAtleti = Ref.create(teamKey);
	}

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void create() throws Exception {
		createClassificationEntry(teamRefAtleti, copaPrimaveraRef);
		List<ClassificationEntry> classificationList = classificationEntriesDAO.findAll();

		assertEquals(1, classificationList.size());
		ClassificationEntry classificationEntry = classificationList.get(0);
		classificationEntry.getRefs();
		String name = classificationEntry.getTeamEntity().getName();
		assertEquals(ATLETICO_MADRID, name);
		String competitionName = classificationEntry.getCompetition().getName();
		assertEquals(COPA_DE_PRIMAVERA, competitionName);
	}

    @Test
    public void update() throws Exception {
        Key<ClassificationEntry> key = createClassificationEntry(teamRefLeganes, copaPrimaveraRef);
        ClassificationEntry c = Ref.create(key).getValue();
        c.setTeamRef(teamRefAtleti);
        classificationEntriesDAO.update(c);
		ClassificationEntry classificationEntry = classificationEntriesDAO.findById(key.getId());
		assertEquals(teamRefAtleti, classificationEntry.getTeamRef());
    }

    @Test
    public void remove() throws Exception {
        Key<ClassificationEntry> key = createClassificationEntry(teamRefAtleti, copaPrimaveraRef);
        ClassificationEntry c = Ref.create(key).getValue();
        classificationEntriesDAO.remove(c);
        assertEquals(0, classificationEntriesDAO.findAll().size());
    }

    @Test
    public void remove_List() throws Exception {
		createClassificationEntry(teamRefAtleti, copaPrimaveraRef);
		createClassificationEntry(teamRefAtleti, copaLigaRef);
		createClassificationEntry(teamRefLeganes, copaLigaRef);
		assertEquals(3, classificationEntriesDAO.findAll().size());

		long copaLigaId = copaLigaRef.getKey().getId();

		List<ClassificationEntry> list = classificationEntriesDAO.findByCompetitionId(copaLigaId);
		assertEquals(2, list.size());
		classificationEntriesDAO.remove(list);
        assertEquals(1, classificationEntriesDAO.findAll().size());
		list = classificationEntriesDAO.findByCompetitionId(copaLigaId);
		assertEquals(0, list.size());
    }

    @Test
    public void findClassificationByCompetition() throws Exception {
		createClassificationEntry(teamRefAtleti, copaPrimaveraRef);
		createClassificationEntry(teamRefAtleti, copaLigaRef);
		createClassificationEntry(teamRefLeganes, copaLigaRef);
		long copaPrimaveraId = copaPrimaveraRef.getKey().getId();
		List<ClassificationEntry> classificationEntryList = classificationEntriesDAO.findByCompetitionId(copaPrimaveraId);
		assertEquals(1, classificationEntryList.size());
		long copaLigaId = copaLigaRef.getKey().getId();
		classificationEntryList = classificationEntriesDAO.findByCompetitionId(copaLigaId);
		assertEquals(2, classificationEntryList.size());
    }

    @Test
    public void findAll() throws Exception {
        createClassificationEntry(teamRefAtleti, copaPrimaveraRef);
        createClassificationEntry(teamRefLeganes, copaPrimaveraRef);
        assertEquals(2, classificationEntriesDAO.findAll().size());
    }

	@Test
	public void update_list() throws Exception {
		createClassificationEntry(teamRefAtleti, copaLigaRef);
		createClassificationEntry(teamRefAtleti, copaPrimaveraRef);
		createClassificationEntry(teamRefLeganes, copaPrimaveraRef);
		List<ClassificationEntry> entries = classificationEntriesDAO.findByCompetitionId(copaPrimaveraRef.get().getId());
		assertEquals(2, entries.size());
		for (int i = 0; i < entries.size(); i++) {
			assertEquals(0, entries.get(i).getMatchesPlayed().intValue());
			entries.get(i).setMatchesPlayed(1);
		}
		classificationEntriesDAO.save(entries);
		entries = classificationEntriesDAO.findByCompetitionId(copaPrimaveraRef.get().getId());
		assertEquals(2, entries.size());
		for (int i = 0; i < entries.size(); i++) {
			assertEquals(1, entries.get(i).getMatchesPlayed().intValue());
		}
		entries = classificationEntriesDAO.findAll();
		assertEquals(3, entries.size());
	}

	@Test
	public void testCreateList() throws Exception {
    	List<ClassificationEntry> entries = new ArrayList<>();
		List<ClassificationEntry> all = classificationEntriesDAO.findAll();

		assertEquals(0, all.size());
		ClassificationEntry leganesEntry = new ClassificationEntry();
		leganesEntry.setTeamRef(teamRefLeganes);
		leganesEntry.setCompetitionRef(copaLigaRef);
		leganesEntry.setMatchesPlayed(0);
		entries.add(leganesEntry);
		ClassificationEntry atletiEntry = new ClassificationEntry();
		atletiEntry.setTeamRef(teamRefAtleti);
		atletiEntry.setCompetitionRef(copaLigaRef);
		atletiEntry.setMatchesPlayed(0);
		entries.add(atletiEntry);
		assertEquals(2, entries.size());
		classificationEntriesDAO.save(entries);
		all = classificationEntriesDAO.findAll();
		assertEquals(2, all.size());
	}

	private Key<ClassificationEntry> createClassificationEntry(Ref<Team> refTeam, Ref<Competition> competitionRef) throws Exception {
		ClassificationEntry c = new ClassificationEntry();
		c.setTeamRef(refTeam);
		c.setCompetitionRef(competitionRef);
		c.setMatchesPlayed(0);
		return classificationEntriesDAO.create(c);
	}
}