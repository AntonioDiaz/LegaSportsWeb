package com.adiaz.daos;

import com.adiaz.entities.ClassificationEntry;
import com.adiaz.entities.CompetitionsVO;
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
public class ClassificationEntriesDAOImplTest {

    private static final String ATLETICO_MADRID = "ATLETICO_MADRID";
    private static final String LEGANES = "LEGANES";
    public static final String COPA_DE_PRIMAVERA = "COPA DE PRIMAVERA";
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    ClassificationEntriesDAO classificationEntriesDAO;
    @Autowired
    CompetitionsDAO competitionsDAO;
    private Ref<CompetitionsVO> competitionsVORef;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(ClassificationEntry.class);
        ObjectifyService.register(CompetitionsVO.class);
        CompetitionsVO competitionsVO = new CompetitionsVO();
        competitionsVO.setName(COPA_DE_PRIMAVERA);
        Key<CompetitionsVO> competitionsVOKey = competitionsDAO.create(competitionsVO);
        competitionsVORef = Ref.create(competitionsVOKey);
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void create() throws Exception {
        createClassificationEntry();
        Assert.assertEquals(1, classificationEntriesDAO.findAll().size());
    }

    @Test
    public void update() throws Exception {
        Key<ClassificationEntry> key = createClassificationEntry();
        ClassificationEntry c = Ref.create(key).getValue();
        c.setTeam(LEGANES);
        classificationEntriesDAO.update(c);
        Assert.assertEquals(LEGANES, classificationEntriesDAO.findById(key.getId()).getTeam());
    }

    @Test
    public void remove() throws Exception {
        Key<ClassificationEntry> key = createClassificationEntry();
        ClassificationEntry c = Ref.create(key).getValue();
        classificationEntriesDAO.remove(c);
        Assert.assertEquals(0, classificationEntriesDAO.findAll().size());
    }

    @Test
    public void findClassificationByCompetition() throws Exception {
        createClassificationEntry();
        Assert.assertEquals(1, classificationEntriesDAO.findByCompetitionId(competitionsVORef.getKey().getId()).size());
    }

    @Test
    public void findAll() throws Exception {
        createClassificationEntry();
        createClassificationEntry();
        Assert.assertEquals(2, classificationEntriesDAO.findAll().size());
    }

    private Key<ClassificationEntry> createClassificationEntry() throws Exception {
        ClassificationEntry c = new ClassificationEntry();
        c.setTeam(ATLETICO_MADRID);
        c.setCompetitionRef(competitionsVORef);
        return classificationEntriesDAO.create(c);
    }


}