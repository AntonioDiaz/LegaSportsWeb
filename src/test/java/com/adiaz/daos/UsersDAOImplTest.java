package com.adiaz.daos;

import com.adiaz.entities.Town;
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

import static org.junit.Assert.assertEquals;

/**
 * Created by toni on 11/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class UsersDAOImplTest {

	private static final String USERNAME_PEPITO = "PEPITO";
	private static final String USERNAME_MARGARITO = "USERNAME_MARGARIGO";
	private static final String PASSWORD = "PASSWORD";
	private static final String PASSWORD_UPDATED = "PASSWORD_UPDATED";
	public static final String LEGANES = "LEGANES";

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	UsersDAO usersDAO;

	@Autowired
	TownDAO townDAO;
	private Ref<Town> townRef;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(User.class);
		ObjectifyService.register(Town.class);

		Town town = new Town();
		town.setName(LEGANES);
		Key<Town> townKey = townDAO.create(town);
		townRef = Ref.create(townKey);
		townDAO.create(town);

	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		assertEquals(0, usersDAO.findAllUsers().size());
		createUserCall(USERNAME_PEPITO);
		assertEquals(1, usersDAO.findAllUsers().size());
		User user = usersDAO.findUser(USERNAME_PEPITO);
		assertEquals(USERNAME_PEPITO, user.getUsername());
		assertEquals(LEGANES, user.getTownRef().get().getName());
	}

	@Test
	public void create_withTown() throws Exception {
		User user = new User();
		user.setUsername(USERNAME_PEPITO);
		long townId = townRef.getKey().getId();
		usersDAO.create(user, townId);
		user = usersDAO.findUser(USERNAME_PEPITO);
		assertEquals(USERNAME_PEPITO, user.getUsername());
		assertEquals(LEGANES, user.getTownRef().get().getName());
	}

	@Test
	public void createExistingUser() throws Exception {
		createUserCall(USERNAME_PEPITO);
		createUserCall(USERNAME_PEPITO);
		assertEquals(1, usersDAO.findAllUsers().size());
	}

	@Test
	public void update() throws Exception {
		Key<User> key = createUserCall(USERNAME_PEPITO);
		User user = Ref.create(key).getValue();
		assertEquals(PASSWORD, user.getPassword01());
		user.setPassword01(PASSWORD_UPDATED);
		usersDAO.update(user);
		user = usersDAO.findUser(user.getUsername());
		assertEquals(PASSWORD_UPDATED, user.getPassword01());
	}



	@Test
	public void updateTryUsername() throws Exception {
		/* create user: with the id USERNAME_PEPITO */
		Key<User> key = createUserCall(USERNAME_PEPITO);
		User user = Ref.create(key).getValue();
		assertEquals(1, usersDAO.findAllUsers().size());
		user.setUsername(USERNAME_MARGARITO);
		usersDAO.update(user);
		assertEquals(1, usersDAO.findAllUsers().size());
		assertEquals(null, usersDAO.findUser(USERNAME_MARGARITO));
		assertEquals(user, usersDAO.findUser(USERNAME_PEPITO));
	}

	@Test
	public void remove() throws Exception {
		Key<User> key = createUserCall(USERNAME_PEPITO);
		User user = Ref.create(key).getValue();
		assertEquals(user, usersDAO.findUser(USERNAME_PEPITO));
		usersDAO.remove(user);
		assertEquals(null, usersDAO.findUser(USERNAME_PEPITO));
	}

	@Test
	public void findAllUsers() throws Exception {
		createUserCall(USERNAME_PEPITO);
		createUserCall(USERNAME_MARGARITO);
		assertEquals(2, usersDAO.findAllUsers().size());
	}

	@Test
	public void findByTown() throws Exception {
		assertEquals(0, usersDAO.findByTown(townRef.get().getId()).size());
		createUserCall(USERNAME_PEPITO);
		createUserCall(USERNAME_MARGARITO);
		assertEquals(2, usersDAO.findByTown(townRef.get().getId()).size());
	}

	@Test
	public void findUser() throws Exception {
		Key<User> key = createUserCall(USERNAME_PEPITO);
		User user = Ref.create(key).getValue();
		assertEquals(user, usersDAO.findUser(USERNAME_PEPITO));
	}

	private Key<User> createUserCall(String userName) throws Exception {
		User user = new User();
		user.setUsername(userName);
		user.setPassword01(PASSWORD);
		user.setTownRef(townRef);
		return usersDAO.create(user);
	}
}