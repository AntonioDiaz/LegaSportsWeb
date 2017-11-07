package com.adiaz.services;

import com.adiaz.daos.CategoriesDAO;
import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.TeamDAO;
import com.adiaz.entities.Category;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Team;
import com.adiaz.forms.CategoriesForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by toni on 20/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoriesManagerImplTest {

	public static final long ID_CATEGORY_01 = 1l;
	public static final long ID_CATEGORY_02 = 2l;

	@Mock
	CategoriesDAO categoriesDAO;

	@Mock
	TeamDAO teamDAO;

	@Mock
	CompetitionsDAO competitionsDAO;

	@Captor
	ArgumentCaptor<Category> categoryArgumentCaptor;

	@InjectMocks
	CategoriesManager categoriesManager = new CategoriesManagerImpl();


	@Test
	public void add_entity_done() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		categoriesManager.add(category);
		verify(categoriesDAO).create(categoryArgumentCaptor.capture());
		assertEquals(category, categoryArgumentCaptor.getValue());
	}

	@Test
	public void add_entityform_done() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		CategoriesForm categoriesForm = new CategoriesForm(category);
		categoriesManager.add(categoriesForm);
		verify(categoriesDAO).create(categoryArgumentCaptor.capture());
		assertEquals(category, categoryArgumentCaptor.getValue());
	}

	@Test
	public void remove_categoryWithNoTeamNorCompetitions_done() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		//given
		when(teamDAO.findByCategory(ID_CATEGORY_01)).thenReturn(new ArrayList<Team>());
		when(competitionsDAO.findByCategory(ID_CATEGORY_01)).thenReturn(new ArrayList<Competition>());
		//when
		categoriesManager.remove(category.getId());
		//then
		verify(categoriesDAO).remove(category);
	}

	@Test
	public void remove_categoryWithTeamsAndCompetitions_notDone() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		List<Team> teamsList = Arrays.asList(new Team());
		List<Competition> competitionsList = new ArrayList<>();
		//given
		when(teamDAO.findByCategory(ID_CATEGORY_01)).thenReturn(teamsList);
		when(competitionsDAO.findByCategory(ID_CATEGORY_01)).thenReturn(competitionsList);
		//when
		categoriesManager.remove(category.getId());
		//then
		verify(categoriesDAO, Mockito.never()).remove(category);
	}


	@Test
	public void update_category_done() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		categoriesManager.update(category);
		verify(categoriesDAO).update(category);
	}


	@Test
	public void update_categoryForm_done() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		CategoriesForm categoriesForm = new CategoriesForm(category);
		//given
		when(categoriesDAO.findById(ID_CATEGORY_01)).thenReturn(category);
		//when
		categoriesManager.update(categoriesForm);
		//then
		verify(categoriesDAO).update(category);
	}

	@Test
	public void update_categoryFormNotExisting_throwsException() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		CategoriesForm categoriesForm = new CategoriesForm(category);
		//given
		when(categoriesDAO.findById(ID_CATEGORY_01)).thenReturn(null);
		//when
		boolean updateDone = categoriesManager.update(categoriesForm);
		//then
		verify(categoriesDAO, never()).update(category);
		assertFalse(updateDone);
	}

	@Test
	public void queryCategories() throws Exception {
		//given
		//when
		categoriesManager.queryCategories();
		//then
		verify(categoriesDAO).findAll();
	}

	@Test
	public void queryCategoriesById() throws Exception {
		//given
		//when
		Category categoryFound = categoriesManager.queryCategoriesById(ID_CATEGORY_01);
		//then
		verify(categoriesDAO).findById(ID_CATEGORY_01);
	}

	@Test
	public void queryCategoriesFormById() throws Exception {
		Category category = createCategory(ID_CATEGORY_01);
		//when
		when(categoriesDAO.findById(ID_CATEGORY_01)).thenReturn(category);
		CategoriesForm categoriesForm = categoriesManager.queryCategoriesFormById(ID_CATEGORY_01);
		//then
		verify(categoriesDAO).findById(ID_CATEGORY_01);
		assertEquals(category, categoriesForm.formToEntity());
	}

	@Test
	public void removeAll() throws Exception {
		Category category01 = createCategory(ID_CATEGORY_01);
		Category category02 = createCategory(ID_CATEGORY_02);
		List<Category> list = Arrays.asList(category01, category02);
		//given
		when(categoriesDAO.findAll()).thenReturn(list);
		//when
		categoriesManager.removeAll();
		//then
		verify(categoriesDAO).remove(category01);
		verify(categoriesDAO).remove(category02);
	}

	@Test
	public void isElegibleForDelete_true() throws Exception {
		//given
		when(teamDAO.findByCategory(ID_CATEGORY_01)).thenReturn(new ArrayList<Team>());
		when(competitionsDAO.findByCategory(ID_CATEGORY_01)).thenReturn(new ArrayList<Competition>());
		//when
		boolean elegibleForDelete = categoriesManager.isElegibleForDelete(ID_CATEGORY_01);
		//then
		assertTrue(elegibleForDelete);
	}

	@Test
	public void isElegibleForDelete_false() throws Exception {
		List<Team> teamsList = Arrays.asList(new Team());
		//given
		when(teamDAO.findByCategory(ID_CATEGORY_01)).thenReturn(teamsList);
		when(competitionsDAO.findByCategory(ID_CATEGORY_01)).thenReturn(new ArrayList<Competition>());
		//when
		boolean elegibleForDelete = categoriesManager.isElegibleForDelete(ID_CATEGORY_01);
		//then
		assertFalse(elegibleForDelete);
	}

	private Category createCategory(long id) {
		Category category = new Category();
		category.setId(id);
		return category;
	}

}