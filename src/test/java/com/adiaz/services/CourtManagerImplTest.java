package com.adiaz.services;

import com.adiaz.daos.CenterDAO;
import com.adiaz.daos.CourtDAO;
import com.adiaz.daos.MatchesDAO;
import com.adiaz.daos.SportsDAO;
import com.adiaz.entities.Center;
import com.adiaz.entities.Court;
import com.adiaz.entities.Match;
import com.adiaz.entities.Sport;
import com.adiaz.forms.CourtForm;
import com.googlecode.objectify.Key;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by toni on 31/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CourtManagerImplTest {

	private static final Long COURT_ID_A = 1l;
	private static final Long COURT_ID_B = 2l;
	@InjectMocks
	CourtManager courtManagerTested = new CourtManagerImpl();

	@Mock
	CourtDAO courtDAOMock;
	@Mock
	MatchesDAO matchesDAOMock;
	@Mock
	SportsDAO sportsDAOMock;
	@Mock
	CenterDAO centerDAO;


	@Test
	public void addSportCourt() throws Exception {
		Court court = createCourt(COURT_ID_A);
		courtManagerTested.addSportCourt(court);
		verify(courtDAOMock).createReturnRef(court);
	}

	@Test
	public void updateSportCourt() throws Exception {
		Court court = createCourt(COURT_ID_A);
		courtManagerTested.updateSportCourt(court);
		verify(courtDAOMock).update(court);
	}

	@Test
	public void removeSportCourt() throws Exception {
		Court court = createCourt(COURT_ID_A);
		when(courtDAOMock.findById(COURT_ID_A)).thenReturn(court);
		courtManagerTested.removeSportCourt(COURT_ID_A);
		verify(courtDAOMock).findById(COURT_ID_A);
		verify(courtDAOMock).remove(court);
	}

	@Test
	public void querySportCourt_findAll() throws Exception {
		courtManagerTested.querySportCourt();
		verify(courtDAOMock).findAll();
	}

	@Test
	public void removeAll() throws Exception {
		Court courtA = createCourt(COURT_ID_A);
		Court courtB = createCourt(COURT_ID_B);
		List<Court> courts = Arrays.asList(courtA, courtB);
		when(courtDAOMock.findAll()).thenReturn(courts);
		courtManagerTested.removeAll();
		verify(courtDAOMock).findAll();
		verify(courtDAOMock).remove(courtA);
		verify(courtDAOMock).remove(courtB);

	}

	@Test
	public void querySportCourt_findById_found() throws Exception {
		Court courtA = createCourt(COURT_ID_A);
		when(courtDAOMock.findById(COURT_ID_A)).thenReturn(courtA);
		Court court = courtManagerTested.querySportCourt(COURT_ID_A);
		verify(courtDAOMock).findById(COURT_ID_A);
		assertEquals(courtA, court);
	}

	@Test
	public void addSportCourt_addingForm() throws Exception {
		Court courtA = createCourt(COURT_ID_A);
		CourtForm courtForm = new CourtForm(courtA);
		Key key = mock(Key.class);
		when(courtDAOMock.create(courtA)).thenReturn(key);
		courtManagerTested.addSportCourt(courtForm);
		verify(courtDAOMock).createReturnRef(courtA);
	}

	@Test
	public void querySportCourts_filterByIdCenter() throws Exception {
		courtManagerTested.querySportCourts(1l);
		verify(courtDAOMock).findBySportCenter(1l);
	}

	@Test
	public void updateSportCourt_form() throws Exception {
		Court courtA = createCourt(COURT_ID_A);
		CourtForm courtForm = new CourtForm(courtA);
		courtManagerTested.updateSportCourt(courtForm);
		verify(courtDAOMock).update(courtA);
	}

	@Test
	public void isElegibleForDelete_TRUE() throws Exception {
		List<Match> list = new ArrayList<>();
		when(matchesDAOMock.findByCourt(COURT_ID_A)).thenReturn(list);
		boolean elegibleForDelete = courtManagerTested.isElegibleForDelete(COURT_ID_A);
		assertTrue(elegibleForDelete);
	}

	@Test
	public void isElegibleForDelete_FALSE() throws Exception {
		List<Match> list = Arrays.asList(new Match());
		when(matchesDAOMock.findByCourt(COURT_ID_A)).thenReturn(list);
		boolean elegibleForDelete = courtManagerTested.isElegibleForDelete(COURT_ID_A);
		assertFalse(elegibleForDelete);
	}

	@Test
	public void querySportCourtsByTownAndSport() throws Exception {
		Sport sport = new Sport();
		sport.setId(69L);
		Center centerA = new Center();
		centerA.setId(1l);
		Center centerB = new Center();
		centerB.setId(2l);
		List centersList = Arrays.asList(centerA, centerB);
		Court courtA = mock(Court.class);
		List<Sport> sportsInCourt = Arrays.asList(sport);
		when(courtA.getSportsDeref()).thenReturn(sportsInCourt);
		List courtsList = Arrays.asList(courtA);
		when(centerDAO.findByTown(1l)).thenReturn(centersList);
		when(sportsDAOMock.findById(1l)).thenReturn(sport);
		when(courtDAOMock.findBySportCenter(1l)).thenReturn(courtsList);
		List<Court> courts = courtManagerTested.querySportCourtsByTownAndSport(1l, 1l);
		assertEquals(1, courts.size());
		assertEquals(courtA, courts.get(0));
	}

	private Court createCourt(Long id) {
		Court court = new Court();
		court.setId(id);
		return court;
	}

}