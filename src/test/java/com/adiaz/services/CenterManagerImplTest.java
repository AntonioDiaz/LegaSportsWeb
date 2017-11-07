package com.adiaz.services;

import com.adiaz.daos.CenterDAO;
import com.adiaz.daos.CourtDAO;
import com.adiaz.entities.Center;
import com.adiaz.entities.Court;
import com.adiaz.forms.CenterForm;
import com.googlecode.objectify.Ref;
import com.sun.xml.internal.bind.v2.model.core.ID;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by toni on 23/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CenterManagerImplTest {

	private static final Long ID_CENTER = 1l;
	@Mock
	CenterDAO centerDAO;

	@Mock
	CourtDAO courtDAO;

	@InjectMocks
	CenterManager centerManager = new CenterManagerImpl();

	@Test
	public void addCenterEntity_create_OK() throws Exception {
		Center center = createCenter(ID_CENTER);
		centerManager.addCenter(center);
		verify(centerDAO).create(center);
	}

	@Test
	public void addCenterForm_create_OK() throws Exception {
		Center center = createCenter(ID_CENTER);
		CenterForm centerForm = new CenterForm(center);
		centerManager.addCenter(centerForm);
		verify(centerDAO).create(center);
	}

	@Test
	public void updateSportCenter_update_OK() throws Exception {
		Center center = createCenter(ID_CENTER);
		CenterForm centerForm = new CenterForm(center);
		centerManager.updateSportCenter(centerForm);
		verify(centerDAO).update(center);
	}

	@Test
	public void querySportCenters_queryAll_ok() throws Exception {
		centerManager.querySportCenters();
		verify(centerDAO).findAll();
	}

	@Test
	public void querySportCenters_queryByTown_found() throws Exception {
		centerManager.querySportCenters(1l);
		verify(centerDAO).findByTown(1l);
	}

	@Test
	public void removeAll_simple_OK() throws Exception {
		Center center = createCenter(ID_CENTER);
		List<Center> list = Arrays.asList(center);
		when(centerDAO.findAll()).thenReturn(list);
		centerManager.removeAll();
		verify(centerDAO, Mockito.times(1)).remove(center);
	}

	@Test
	public void isElegibleForDelete_centerNotHasCourts_TRUE() throws Exception {
		List<Court> list = Arrays.asList();
		when(courtDAO.findBySportCenter(ID_CENTER)).thenReturn(list);
		boolean elegibleForDelete = centerManager.isElegibleForDelete(ID_CENTER);
		assertTrue(elegibleForDelete);
	}
	
	@Test
	public void isElegibleForDelete_centerHasCourts_FALSE() throws Exception {
		List<Court> list = Arrays.asList(new Court());
		when(courtDAO.findBySportCenter(ID_CENTER)).thenReturn(list);
		boolean elegibleForDelete = centerManager.isElegibleForDelete(ID_CENTER);
		assertFalse(elegibleForDelete);
	}

	@Test
	public void queryFormById_foundResult_TRUE() throws Exception {
		Center center = createCenter(ID_CENTER);
		when(centerDAO.findById(ID_CENTER)).thenReturn(center);
		CenterForm centerForm = centerManager.queryFormById(ID_CENTER);
		assertEquals(centerForm.formToEntity(), center);
	}

	@Test
	public void queryFormById_foundResult_FALSE() throws Exception {
		Center center = createCenter(ID_CENTER);
		when(centerDAO.findById(ID_CENTER)).thenReturn(null);
		CenterForm centerForm = centerManager.queryFormById(ID_CENTER);
		assertNotEquals(centerForm.formToEntity(), center);
		assertNull(centerForm.getId());
	}

	@Test
	public void queryById() throws Exception {
		centerManager.queryById(ID_CENTER);
		verify(centerDAO).findById(ID_CENTER);
	}

	@Test
	public void removeSportCenter() throws Exception {
		centerManager.removeSportCenter(ID_CENTER);
		verify(centerDAO).remove(ID_CENTER);
	}

	private Center createCenter(Long idCenter) {
		Center center = new Center();
		center.setId(idCenter);
		return center;
	}
}