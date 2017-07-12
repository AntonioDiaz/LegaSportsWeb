package com.adiaz.daos;

import com.adiaz.entities.CategoriesVO;
import com.googlecode.objectify.Key;
import org.junit.Assert;
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

    public static final String CATEGORY_CADETE = "Cadete";
    public static final String CATEGORY_JUVENIL = "Juvenil";
    public static final Integer CATEGORY_ORDER_2 = 2;
    private static final Integer CATEGORY_ORDER_3 = 3;


    @org.junit.Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(CategoriesVO.class);
    }

    public void teadDown() throws Exception {
        helper.tearDown();
    }

    private Key<CategoriesVO> createCategory() throws Exception {
        CategoriesVO category = new CategoriesVO();
        category.setName(CATEGORY_CADETE);
        category.setOrder(CATEGORY_ORDER_2);
        Key<CategoriesVO> categoriesVOKey = categoriesDAO.create(category);
        return categoriesVOKey;
    }

    @org.junit.Test
    public void create() throws Exception {
        Assert.assertEquals(0, categoriesDAO.findAllCategories().size());
        createCategory();
        List<CategoriesVO> categories = categoriesDAO.findAllCategories();
        Assert.assertEquals(1, categories.size());
        CategoriesVO categoriesVO = categories.get(0);
        Assert.assertEquals(CATEGORY_CADETE, categoriesVO.getName());
        Assert.assertEquals(CATEGORY_ORDER_2, categoriesVO.getOrder());
    }



    @org.junit.Test
    public void update() throws Exception {
        createCategory();
        List<CategoriesVO> categories = categoriesDAO.findAllCategories();
        CategoriesVO categoriesVO = categories.get(0);
        categoriesVO.setName(CATEGORY_JUVENIL);
        categoriesVO.setOrder(CATEGORY_ORDER_3);
        categoriesDAO.update(categoriesVO);
        categoriesVO = categoriesDAO.findCategoryById(categoriesVO.getId());
        Assert.assertEquals(CATEGORY_JUVENIL, categoriesVO.getName());
        Assert.assertEquals(CATEGORY_ORDER_3, categoriesVO.getOrder());
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
        Key<CategoriesVO> key = createCategory();
        CategoriesVO categoriesVO = categoriesDAO.findCategoryById(key.getId());
        Assert.assertEquals(key.getId(), (long)categoriesVO.getId());
        Assert.assertEquals(CATEGORY_CADETE, categoriesVO.getName());
        Assert.assertEquals(CATEGORY_ORDER_2, categoriesVO.getOrder());
    }

}