package com.adiaz.daos;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
/**
 * Created by toni on 10/07/2017.
 */
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)


public class CategoriesDAOTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    private CategoriesDAO categoriesDAO;

    @org.junit.Before
    public void setUp() throws Exception {
        helper.setUp();
    }

    @org.junit.Test
    public void create() throws Exception {
        helper.tearDown();
    }

    @org.junit.Test
    public void update() throws Exception {

        Assert.assertTrue(categoriesDAO!=null);
        Assert.assertTrue(categoriesDAO.findAllCategories()!=null);
    }

    @org.junit.Test
    public void remove() throws Exception {
    }

    @org.junit.Test
    public void findAllCategories() throws Exception {
    }

    @org.junit.Test
    public void findCategoryById() throws Exception {
    }

}