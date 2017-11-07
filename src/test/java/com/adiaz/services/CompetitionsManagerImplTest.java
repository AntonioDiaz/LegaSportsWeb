package com.adiaz.services;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.entities.Competition;
import com.adiaz.forms.CompetitionsForm;
import com.googlecode.objectify.Key;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by toni on 24/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CompetitionsManagerImplTest {

	private static final java.lang.Long ID_COMPETITION_A = 1l;
	private static final java.lang.Long ID_COMPETITION_B = 2l;
	private static final java.lang.Long ID_COMPETITION_Z = 3l;
	private static final long ID_TOWN = 66l;
	@InjectMocks
	CompetitionsManager competitionsManager = new CompetitionsManagerImpl();

	@Mock
	CompetitionsDAO competitionsDAO;

	@Mock
	MatchesManager matchesManager;

	@Mock
	ClassificationManager classificationManager;

	@Mock
	SanctionsManager sancionsManager;



	@Test
	public void add_competitionEntity_OK() throws Exception {
		Competition competition = new Competition();
		Key key = mock(Key.class);
		when(key.getId()).thenReturn(ID_COMPETITION_A);
		when(competitionsDAO.create(competition)).thenReturn(key);
		Long newCompetitionId = competitionsManager.add(competition);
		verify(competitionsDAO).create(competition);
		assertEquals(ID_COMPETITION_A, newCompetitionId);
	}

	@Test
	public void add_competitionForm_OK() throws Exception {
		Competition competition = new Competition();
		CompetitionsForm competitionsForm = new CompetitionsForm(competition);
		Key key = mock(Key.class);
		//given
		when(key.getId()).thenReturn(ID_COMPETITION_A);
		when(competitionsDAO.create(competition)).thenReturn(key);
		//when
		Long newCompetitionId = competitionsManager.add(competitionsForm);
		//then
		verify(competitionsDAO).create(competition);
		assertEquals(ID_COMPETITION_A, newCompetitionId);
	}

	@Test
	public void remove() throws Exception {
		Competition competition = createCompetition(ID_COMPETITION_A);
		//given
		//when
		competitionsManager.remove(competition);
		//then
		verify(matchesManager).removeByCompetition(ID_COMPETITION_A);
		verify(classificationManager).removeByCompetition(ID_COMPETITION_A);
		verify(sancionsManager).removeByCompetition(ID_COMPETITION_A);
		verify(competitionsDAO).remove(competition);
	}

	@Test
	public void update_entity() throws Exception {
		Competition competition = createCompetition(ID_COMPETITION_A);
		//given
		//when
		competitionsManager.update(competition);
		//then
		verify(competitionsDAO).update(competition);
	}

	@Test
	public void update_form() throws Exception {
		Competition competition = createCompetition(ID_COMPETITION_A);
		CompetitionsForm competitionsForm = new CompetitionsForm(competition);
		//given
		when(competitionsDAO.findById(ID_COMPETITION_A)).thenReturn(competition);
		//when
		competitionsManager.update(ID_COMPETITION_A, competitionsForm);
		//then
		verify(competitionsDAO).update(competition);
	}

	@Test
	public void queryCompetitions() throws Exception {
		competitionsManager.queryCompetitions();
		verify(competitionsDAO).findAll();
	}

	@Test
	public void queryCompetitionsBySport() throws Exception {
		competitionsManager.queryCompetitionsBySport(1l);
		verify(competitionsDAO).findBySport(1l);
	}

	@Test
	public void queryCompetitionsById() throws Exception {
		Competition competition = createCompetition(ID_COMPETITION_A);
		//given
		when(competitionsDAO.findById(ID_COMPETITION_A)).thenReturn(competition);
		//when
		CompetitionsForm competitionsForm = competitionsManager.queryCompetitionsById(ID_COMPETITION_A);
		//then
		verify(competitionsDAO).findById(ID_COMPETITION_A);
		assertEquals(competition, competitionsForm.formToEntity());
	}

	@Test
	public void queryCompetitionsByIdEntity() throws Exception {
		//given
		//when
		competitionsManager.queryCompetitionsByIdEntity(ID_COMPETITION_A);
		//then
		verify(competitionsDAO).findById(ID_COMPETITION_A);
	}

	@Test
	public void queryCompetitionsByTown() throws Exception {
		Competition competitionA = createCompetition(ID_COMPETITION_A);
		Competition competitionB = createCompetition(ID_COMPETITION_B);
		Competition competitionZ = createCompetition(ID_COMPETITION_Z);
		competitionA.setName("competitionA");
		competitionB.setName("competitionB");
		competitionZ.setName("competitionZ");
		List<Competition> competitionsList = Arrays.asList(competitionZ, competitionB, competitionA);
		//given
		when(competitionsDAO.find(ID_TOWN, true)).thenReturn(competitionsList);
		//when
		List<Competition> competitions = competitionsManager.queryCompetitionsByTown(ID_TOWN, true);
		//then
		assertEquals(3, competitions.size());
		assertEquals(competitionA, competitions.get(0));
		assertEquals(competitionB, competitions.get(1));
		assertEquals(competitionZ, competitions.get(2));
	}

	@Test
	public void queryCompetitions_filter_competitions() throws Exception {
		long idSport = 1l;
		long idCategory = 2l;
		long idTown = 3l;
		Competition competitionA = createCompetition(ID_COMPETITION_A);
		Competition competitionB = createCompetition(ID_COMPETITION_B);
		Competition competitionZ = createCompetition(ID_COMPETITION_Z);
		competitionA.setName("competitionA");
		competitionB.setName("competitionB");
		competitionZ.setName("competitionZ");
		//given
		List<Competition> competitionsList = Arrays.asList(competitionZ, competitionA, competitionB);
		given(competitionsDAO.find(idSport, idCategory, idTown)).willReturn(competitionsList);
		//when
		List<Competition> competitionsQuery = competitionsManager.queryCompetitions(idSport, idCategory, idTown);
		//then
		verify(competitionsDAO).find(idSport, idCategory, idTown);
		assertEquals(3, competitionsQuery.size());
		assertEquals(competitionA, competitionsQuery.get(0));
		assertEquals(competitionB, competitionsQuery.get(1));
		assertEquals(competitionZ, competitionsQuery.get(2));
	}

	@Test
	public void queryCompetitions2() throws Exception {
		queryCompetitions_filter_competitions();
	}

	@Test
	public void removeAll() throws Exception {
		Competition competitionA = createCompetition(ID_COMPETITION_A);
		Competition competitionB = createCompetition(ID_COMPETITION_B);
		List<Competition> competitionsList = Arrays.asList(competitionA, competitionB);
		//given
		given(competitionsDAO.findAll()).willReturn(competitionsList);
		//when
		competitionsManager.removeAll();
		//then
		verify(competitionsDAO).remove(competitionA);
		verify(competitionsDAO).remove(competitionB);
	}

	private Competition createCompetition(Long idCompetition) {
		Competition competition = new Competition();
		competition.setId(idCompetition);
		return  competition;
	}
}