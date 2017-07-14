package com.adiaz.daos;

import com.adiaz.entities.User;
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
public class UsersDAOImplTest {

    private static final String USERNAME_PEPITO = "PEPITO";
    private static final String USERNAME_MARGARITO = "USERNAME_MARGARIGO";
    private static final String PASSWORD = "PASSWORD";
    private static final String PASSWORD_UPDATED = "PASSWORD_UPDATED";

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    UsersDAO usersDAO;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        ObjectifyService.register(User.class);
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void create() throws Exception {
        Assert.assertEquals(0, usersDAO.findAllUsers().size());
        createUserCallPepito();
        Assert.assertEquals(1, usersDAO.findAllUsers().size());
        User user = usersDAO.findUser(USERNAME_PEPITO);
        Assert.assertEquals(USERNAME_PEPITO, user.getUsername());
    }


    @Test
    public void createExistingUser() throws Exception {
        createUserCallPepito();
        createUserCallPepito();
        Assert.assertEquals(1, usersDAO.findAllUsers().size());
    }

    @Test
    public void update() throws Exception {
        Key<User> key = createUserCallPepito();
        User user = Ref.create(key).getValue();
        Assert.assertEquals(PASSWORD, user.getPassword01());
        user.setPassword01(PASSWORD_UPDATED);
        usersDAO.update(user);
        user = usersDAO.findUser(user.getUsername());
        Assert.assertEquals(PASSWORD_UPDATED, user.getPassword01());
    }

    @Test
    public void updateTryUsername() throws Exception {
        /* create user: with the id USERNAME_PEPITO */
        Key<User> key = createUserCallPepito();
        User user = Ref.create(key).getValue();
        Assert.assertEquals(1, usersDAO.findAllUsers().size());
        user.setUsername(USERNAME_MARGARITO);
        usersDAO.update(user);
        Assert.assertEquals(1, usersDAO.findAllUsers().size());
        Assert.assertEquals(null, usersDAO.findUser(USERNAME_MARGARITO));
        Assert.assertEquals(user, usersDAO.findUser(USERNAME_PEPITO));
    }

    @Test
    public void remove() throws Exception {
        Key<User> key = createUserCallPepito();
        User user = Ref.create(key).getValue();
        Assert.assertEquals(user, usersDAO.findUser(USERNAME_PEPITO));
        usersDAO.remove(user);
        Assert.assertEquals(null, usersDAO.findUser(USERNAME_PEPITO));
    }

    @Test
    public void findAllUsers() throws Exception {
        createUserCallPepito();
        createUserCallMargarito();
        Assert.assertEquals(2, usersDAO.findAllUsers().size());
    }

    @Test
    public void findUser() throws Exception {
        Key<User> key = createUserCallPepito();
        User user = Ref.create(key).getValue();
        Assert.assertEquals(user, usersDAO.findUser(USERNAME_PEPITO));
    }

    private Key<User> createUserCallPepito() throws Exception {
        User user = new User();
        user.setUsername(USERNAME_PEPITO);
        user.setPassword01(PASSWORD);
        return usersDAO.create(user);
    }

    private Key<User> createUserCallMargarito() throws Exception {
        User user = new User();
        user.setUsername(USERNAME_MARGARITO);
        user.setPassword01(PASSWORD);
        return usersDAO.create(user);
    }
}