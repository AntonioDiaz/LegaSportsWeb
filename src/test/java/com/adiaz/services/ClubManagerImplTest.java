package com.adiaz.services;

import com.adiaz.daos.ClubDAO;
import com.adiaz.daos.TeamDAO;
import com.adiaz.entities.Club;
import com.adiaz.entities.Team;
import com.adiaz.forms.ClubForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by toni on 24/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClubManagerImplTest {
	private static final Long ID_TOWN = 66l;
	private static final Long ID_CLUB_A = 1l;
	private static final Long ID_CLUB_B = 2l;
	@InjectMocks
	ClubManager clubManager = new ClubManagerImpl();

	@Mock
	ClubDAO clubDAO;

	@Mock
	TeamDAO teamDAO;


	@Test
	public void queryAll() throws Exception {
		clubManager.queryAll();
		verify(clubDAO).findAll();
	}

	@Test
	public void queryByTownId() throws Exception {
		clubManager.queryByTownId(ID_TOWN);
		verify(clubDAO).findByTown(ID_TOWN);
	}

	@Test
	public void queryById() throws Exception {
		Club club = createClub(ID_CLUB_A);
		when(clubDAO.findById(ID_CLUB_A)).thenReturn(club);
		ClubForm clubForm = clubManager.queryById(ID_TOWN);
		verify(clubDAO).findById(ID_TOWN);
		assertEquals(club, clubForm.formToEntity());
	}

	@Test
	public void add() throws Exception {
		Club club = createClub(ID_CLUB_A);
		ClubForm clubForm = new ClubForm(club);
		clubManager.add(clubForm);
		verify(clubDAO).create(club);
	}

	@Test
	public void update() throws Exception {
		Club club = createClub(ID_CLUB_A);
		clubManager.update(new ClubForm(club));
		verify(clubDAO).update(club);
	}

	@Test
	public void remove_clubWithNoTeams_OK() throws Exception {
		Club club = createClub(ID_CLUB_A);
		when(teamDAO.findByClub(ID_CLUB_A)).thenReturn(new ArrayList<Team>());
		when(clubDAO.remove(club)).thenReturn(true);
		boolean remove = clubManager.remove(ID_CLUB_A);
		assertTrue(remove);
		verify(clubDAO, times(1)).remove(club);
	}

	@Test
	public void remove_clubWithTeams_ERROR() throws Exception {
		Team teamA = new Team();
		Team teamB = new Team();
		List<Team> teamsList = Arrays.asList(teamA, teamB);
		when(teamDAO.findByClub(ID_CLUB_A)).thenReturn(teamsList);
		boolean remove = clubManager.remove(ID_CLUB_A);
		assertFalse(remove);
		verify(clubDAO, Mockito.never()).remove(any(Club.class));
	}

	@Test
	public void removaAll() throws Exception {
		Club clubA = createClub(ID_CLUB_A);
		Club clubB = createClub(ID_CLUB_B);
		List<Club> clubsList = Arrays.asList(clubA, clubB);
		when(clubDAO.findAll()).thenReturn(clubsList);
		clubManager.removaAll();
		verify(clubDAO).remove(clubA);
		verify(clubDAO).remove(clubB);
	}

	@Test
	public void isElegibleForDelete_FALSE() throws Exception {
		Team teamA = new Team();
		Team teamB = new Team();
		List<Team> teamsList = Arrays.asList(teamA, teamB);
		when(teamDAO.findByClub(ID_CLUB_A)).thenReturn(teamsList);
		boolean elegibleForDelete = clubManager.isElegibleForDelete(ID_CLUB_A);
		assertFalse(elegibleForDelete);
	}
	@Test
	public void isElegibleForDelete_TRUE() throws Exception {
		List<Team> teamsList = Arrays.asList();
		when(teamDAO.findByClub(ID_CLUB_A)).thenReturn(teamsList);
		boolean elegibleForDelete = clubManager.isElegibleForDelete(ID_CLUB_A);
		assertTrue(elegibleForDelete);
	}

	private Club createClub(Long idClub) {
		Club club = new Club();
		club.setId(idClub);
		return club;
	}
}