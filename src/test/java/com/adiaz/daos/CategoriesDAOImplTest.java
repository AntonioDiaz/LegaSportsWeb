package com.adiaz.daos;

import com.adiaz.entities.Category;
import com.adiaz.entities.Town;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

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
        assertEquals(0, categoriesDAO.findAllCategories().size());
        createCategory();
        List<Category> categories = categoriesDAO.findAllCategories();
        assertEquals(1, categories.size());
        Category category = categories.get(0);
        assertEquals(CATEGORY_CADETE, category.getName());
        assertEquals(CATEGORY_ORDER_2, category.getOrder());
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
        assertEquals(CATEGORY_JUVENIL, category.getName());
        assertEquals(CATEGORY_ORDER_3, category.getOrder());
    }

    @org.junit.Test
    public void remove() throws Exception {
       createCategory();
       assertEquals(1, categoriesDAO.findAllCategories().size());
       categoriesDAO.remove(categoriesDAO.findAllCategories().get(0));
       assertEquals(0, categoriesDAO.findAllCategories().size());

    }

    @org.junit.Test
    public void findAllCategories() throws Exception {
        createCategory();
        createCategory();
        assertEquals(2, categoriesDAO.findAllCategories().size());
    }

    @org.junit.Test
    public void findCategoryById() throws Exception {
        Key<Category> key = createCategory();
        Category category = categoriesDAO.findCategoryById(key.getId());
        assertEquals(key.getId(), (long) category.getId());
        assertEquals(CATEGORY_CADETE, category.getName());
        assertEquals(CATEGORY_ORDER_2, category.getOrder());
    }

}