package com.adiaz.daos;

import com.adiaz.entities.Category;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/** Created by toni on 10/07/2017. */

@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoriesDAOImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    private CategoriesDAO categoriesDAO;
    @Autowired
    private TownDAO townDAO;

    public static final String CATEGORY_CADETE = "Cadete";
    public static final String CATEGORY_JUVENIL = "Juvenil";
    public static final Integer CATEGORY_ORDER_2 = 2;
    private static final Integer CATEGORY_ORDER_3 = 3;

    private Ref<Town> refTownLeganes;

    @org.junit.Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(Category.class);
        ObjectifyService.register(Town.class);
    }

    public void teadDown() throws Exception {
        helper.tearDown();
    }

    private Key<Category> createCategory() throws Exception {
        Category category = new Category();
        category.setName(CATEGORY_CADETE);
        category.setOrder(CATEGORY_ORDER_2);
        Key<Category> categoryKey = categoriesDAO.create(category);
        return categoryKey;
    }

    @org.junit.Test
    public void create() throws Exception {
        Assert.assertEquals(0, categoriesDAO.findAllCategories().size());
        createCategory();
        List<Category> categories = categoriesDAO.findAllCategories();
        Assert.assertEquals(1, categories.size());
        Category category = categories.get(0);
        Assert.assertEquals(CATEGORY_CADETE, category.getName());
        Assert.assertEquals(CATEGORY_ORDER_2, category.getOrder());
    }

    @org.junit.Test
    public void update() throws Exception {
        createCategory();
        List<Category> categories = categoriesDAO.findAllCategories();
        Category category = categories.get(0);
        category.setName(CATEGORY_JUVENIL);
        category.setOrder(CATEGORY_ORDER_3);
        categoriesDAO.update(category);
        category = categoriesDAO.findCategoryById(category.getId());
        Assert.assertEquals(CATEGORY_JUVENIL, category.getName());
        Assert.assertEquals(CATEGORY_ORDER_3, category.getOrder());
    }

    @org.junit.Test
    public void remove() throws Exception {
       createCategory();
       Assert.assertEquals(1, categoriesDAO.findAllCategories().size());
       categoriesDAO.remove(categoriesDAO.findAllCategories().get(0));
       Assert.assertEquals(0, categoriesDAO.findAllCategories().size());

    }

    @org.junit.Test
    public void findAllCategories() throws Exception {
        createCategory();
        createCategory();
        Assert.assertEquals(2, categoriesDAO.findAllCategories().size());
    }

    @org.junit.Test
    public void findCategoryById() throws Exception {
        Key<Category> key = createCategory();
        Category category = categoriesDAO.findCategoryById(key.getId());
        Assert.assertEquals(key.getId(), (long) category.getId());
        Assert.assertEquals(CATEGORY_CADETE, category.getName());
        Assert.assertEquals(CATEGORY_ORDER_2, category.getOrder());
    }

}