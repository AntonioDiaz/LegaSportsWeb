package com.adiaz.services;

import com.adiaz.daos.ClassificationEntriesDAO;
import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.MatchesDAO;
import com.adiaz.daos.SanctionsDAO;
import com.adiaz.entities.ClassificationEntry;
import com.adiaz.entities.Competition;
import com.adiaz.entities.Team;
import com.googlecode.objectify.Ref;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by toni on 23/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClassificationManagerImplTest {

	private static final Long ID_ENTRY_01 = 1l;
	private static final Long ID_ENTRY_02 = 2l;
	private static final Long ID_COMPETITION = 22l;
	@Mock
	ClassificationEntriesDAO classificationEntriesDAO;

	@Mock
	MatchesDAO matchesDAO;

	@Mock
	CompetitionsDAO competitionsDAO;

	@Mock
	SanctionsDAO sanctionsDAO;

	@InjectMocks
	ClassificationManager classificationManager = new ClassificationManagerImpl();

	@Test
	public void add_classificationEntry_OK() throws Exception {
		ClassificationEntry classificationEntry = new ClassificationEntry();
		classificationManager.add(classificationEntry);
		verify(classificationEntriesDAO).create(classificationEntry);
	}

	@Test
	public void remove_classificationEntry_OK() throws Exception {
		ClassificationEntry classificationEntry = createClassificationEntry(ID_ENTRY_01);
		classificationManager.remove(classificationEntry);
		verify(classificationEntriesDAO).remove(classificationEntry);
	}

	@Test
	public void removeByCompetition_2classificationsEntriesFound_OK() throws Exception {
		ClassificationEntry entry01 = createClassificationEntry(ID_ENTRY_01);
		ClassificationEntry entry02 = createClassificationEntry(ID_ENTRY_01);
		List<ClassificationEntry> list = Arrays.asList();
		when(classificationEntriesDAO.findByCompetition(ID_COMPETITION)).thenReturn(list);
		classificationManager.removeByCompetition(ID_COMPETITION);
		verify(classificationEntriesDAO).remove(list);
	}

	@Test
	public void removeByCompetition_nonClassificationsEntriesFound_OK() throws Exception {
		List<ClassificationEntry> list = Arrays.asList();
		when(classificationEntriesDAO.findByCompetition(ID_COMPETITION)).thenReturn(list);
		classificationManager.removeByCompetition(ID_COMPETITION);
		verify(classificationEntriesDAO).remove(list);
	}

	@Test
	public void update_classificationEntry_ok() throws Exception {
		ClassificationEntry entry = new ClassificationEntry();
		classificationManager.update(entry);
		verify(classificationEntriesDAO).update(entry);
	}

	@Test
	public void queryClassificationByCompetition_OK() throws Exception {
		classificationManager.queryClassificationByCompetition(ID_ENTRY_01);
		verify(classificationEntriesDAO).findByCompetition(ID_ENTRY_01);
	}

	@Test
	public void add_classificationEntriesList_OK() throws Exception {
		ClassificationEntry entry01 = createClassificationEntry(ID_ENTRY_01);
		ClassificationEntry entry02 = createClassificationEntry(ID_ENTRY_02);
		List<ClassificationEntry> list = Arrays.asList(entry01, entry02);
		classificationManager.add(list);
		verify(classificationEntriesDAO).create(entry01);
		verify(classificationEntriesDAO).create(entry02);
	}

	@Test
	public void updateClassificationByCompetition() throws Exception {
		assertTrue(false);
	}

	@Test
	public void initClassification() throws Exception {
		assertTrue(false);
/*		Team team01 = new Team();
		Team team02 = new Team();
		List teamsList = Arrays.asList(team01, team02);
		Competition mockCompetition = mock(Competition.class);
		Ref ref = mock(Ref.class);
		when(mockCompetition.getTeamsDeref()).thenReturn(teamsList);
		when(competitionsDAO.findById(ID_COMPETITION)).thenReturn(mockCompetition);
		when(Ref.create(mockCompetition)).thenReturn(ref);
		classificationManager.initClassification(ID_COMPETITION);
		ArgumentCaptor<ClassificationEntry> argumentCaptor = ArgumentCaptor.forClass(ClassificationEntry.class);
		verify(classificationEntriesDAO).create(argumentCaptor.capture());
		assertEquals(0, argumentCaptor.getValue().getPoints().intValue());*/

	}

	@Test
	public void removeAll() throws Exception {
		ClassificationEntry entry01 = createClassificationEntry(ID_ENTRY_01);
		ClassificationEntry entry02 = createClassificationEntry(ID_ENTRY_02);
		List<ClassificationEntry> list = Arrays.asList(entry01, entry02);
		when(classificationEntriesDAO.findAll()).thenReturn(list);
		classificationManager.removeAll();
		verify(classificationEntriesDAO).remove(entry01);
		verify(classificationEntriesDAO).remove(entry02);
	}

	private ClassificationEntry createClassificationEntry(Long idEntry) {
		ClassificationEntry classificationEntry = new ClassificationEntry();
		classificationEntry.setId(idEntry);
		return classificationEntry;
	}
}