package com.adiaz.daos;

import com.adiaz.entities.Town;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by toni on 14/07/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class TownDAOImplTest {

	public static final String LEGANES = "Leganes";
	public static final String FUENLABRADA = "Fuenlabrada";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		ObjectifyService.register(Town.class);
	}

	@Autowired
	TownDAO townDAO;

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void create() throws Exception {
		Key<Town> key = createTown(LEGANES);
		Town town = townDAO.findById(key.getId());
		Assert.assertEquals(key.getId(), (long) town.getId());
		Assert.assertTrue(town.isActive());
	}

	@Test
	public void update_townName() throws Exception {
		Key<Town> key = createTown(LEGANES);
		Town town = Ref.create(key).getValue();
		town.setName(FUENLABRADA);
		townDAO.update(town);
		Assert.assertEquals(FUENLABRADA, townDAO.findById(town.getId()).getName());
	}
	@Test
	public void update_deactivate() throws Exception {
		Key<Town> key = createTown(LEGANES);
		Town town = Ref.create(key).getValue();
		Assert.assertTrue(townDAO.findById(town.getId()).isActive());
		town.setActive(false);
		townDAO.update(town);
		Assert.assertFalse(townDAO.findById(town.getId()).isActive());
	}

	@Test
	public void remove() throws Exception {
		Key<Town> key = createTown(LEGANES);
		Town town = new Town();
		town.setId(key.getId());
		townDAO.remove(town);
		Assert.assertEquals(0, townDAO.findAll().size());
	}

	@Test
	public void findAll() throws Exception {
		Assert.assertEquals(0, townDAO.findAll().size());
		createTown(LEGANES);
		createTown(LEGANES);
		Assert.assertEquals(2, townDAO.findAll().size());
	}

	@Test
	public void findAll_AlphabeticalOrder() throws Exception {
		createTown(LEGANES);
		createTown(FUENLABRADA);
		Assert.assertEquals(FUENLABRADA, townDAO.findAll().get(0).getName());
		Assert.assertEquals(LEGANES, townDAO.findAll().get(1).getName());
	}


	@Test
	public void findById_existing() throws Exception {
		Key<Town> key = createTown(LEGANES);
		Assert.assertEquals(key.getId(), (long)townDAO.findById(key.getId()).getId());
	}

	@Test
	public void findById_nonExisting() throws Exception {
		Assert.assertEquals(null, townDAO.findById(221l));
	}

	private Key<Town> createTown(String name) throws Exception {
		Town town = new Town();
		town.setName(name);
		town.setActive(true);
		return townDAO.create(town);
	}
}