package com.adiaz.services;

import com.adiaz.daos.ClubDAO;
import com.adiaz.daos.TeamDAO;
import com.adiaz.entities.Club;
import com.adiaz.forms.ClubForm;
import com.adiaz.forms.utils.ClubFormUtils;
import com.googlecode.objectify.Key;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by toni on 24/07/2017.
 */
@Service ("clubManager")
public class ClubManagerImpl implements ClubManager {

	private static final Logger logger = Logger.getLogger(ClubManagerImpl.class.getName());

	@Autowired
	ClubDAO clubDAO;

	@Autowired
	TeamDAO teamDAO;

	@Autowired
	ClubFormUtils clubFormUtils;

	@Override
	public List<Club> queryAll() {
		return clubDAO.findAll();
	}

	@Override
	public List<Club> queryByTownId(Long townId) {
		return clubDAO.findByTown(townId);
	}

	@Override
	public ClubForm queryById(Long id) {
		Club club = clubDAO.findById(id);
		ClubForm clubForm = clubFormUtils.entityToForm(club);
		return clubForm;
	}

	@Override
	public Long add(ClubForm clubForm) throws Exception {
		Club club = clubFormUtils.formToEntity(clubForm);
		Key<Club> clubKey = clubDAO.create(club);
		return clubKey == null ? -1 : clubKey.getId();
	}

	@Override
	public boolean update(ClubForm clubForm) throws Exception {
		Club club = clubFormUtils.formToEntity(clubForm);
		return clubDAO.update(club);
	}

	@Override
	public boolean remove(Long id) throws Exception {
		boolean removeDone = false;
		if (isElegibleForDelete(id)) {
			Club club = new Club();
			club.setId(id);
			removeDone = clubDAO.remove(club);
		}
		return removeDone;
	}

	@Override
	public void removaAll() throws Exception {
		for (Club club : clubDAO.findAll()) {
			clubDAO.remove(club);
		}
	}

	@Override
	public boolean isElegibleForDelete(Long idClub) {
		return teamDAO.findByClub(idClub).isEmpty();
	}
}
