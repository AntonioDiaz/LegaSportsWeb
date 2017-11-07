package com.adiaz.controllers;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.entities.Competition;
import com.adiaz.services.CompetitionsManagerImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;

/**
 * Created by toni on 13/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext-testing.xml")
@WebAppConfiguration("file:web")
public class CompetitionsControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Mock
	private CompetitionsDAO competitionsDAO;

	@InjectMocks
	private CompetitionsManagerImpl competitionsManager;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		//when(this.competitionsDAO.find()).thenReturn(new ArrayList<Competition>());
		when(this.competitionsManager.queryCompetitions()).thenReturn(new ArrayList<Competition>());
		System.out.println("Competitions: " + competitionsManager.queryCompetitions());
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void givenWac_whenServletContext_thenItProvidesCompetitionsController() {
		ServletContext servletContext = wac.getServletContext();
		assertNotNull(servletContext);
		assertTrue(servletContext instanceof MockServletContext);
		assertNotNull(wac.getBean("competitionsController"));
	}

	@Test
	public void listCompetitions() throws Exception {
		mockMvc.perform(get("/competitions/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("competitions_list"));
	}

	@Test
	public void addCompetitions() throws Exception {
		mockMvc.perform(get("/competitions/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("competitions_add"));
	}

	@Test
	public void doAddCompetition() throws Exception {
	}

	@Test
	public void doRemoveCompetition() throws Exception {
	}

	@Test
	public void viewCalendar() throws Exception {
	}

	@Test
	public void viewClassification() throws Exception {
	}

	@Test
	public void loadClassification() throws Exception {
	}

	@Test
	public void doLoadClassification() throws Exception {
	}

	@Test
	public void loadCalendar() throws Exception {
	}

	@Test
	public void doLoadCalendar() throws Exception {
	}

}