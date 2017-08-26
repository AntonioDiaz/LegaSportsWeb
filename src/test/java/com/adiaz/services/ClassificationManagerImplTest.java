package com.adiaz.services;

import com.adiaz.entities.ClassificationEntry;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
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



/* Created by toni on 12/07/2017. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class ClassificationManagerImplTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    ClassificationManager classificationManager;

	@Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(ClassificationEntry.class);

    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void add() throws Exception {
        ClassificationEntry c = new ClassificationEntry();
        //c.setTeam("team");
        classificationManager.add(c);
        assertEquals(1, 1);
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void queryClassificationBySport() throws Exception {
    }

    @Test
    public void add1() throws Exception {
    }

    @Test
    public void removeAll() throws Exception {
    }

}