package com.adiaz.daos;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Sanction;
import com.adiaz.entities.Team;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

/**
 * Created by toni on 28/09/2017.
 */
@Repository
public class SanctionsDAOImpl implements SanctionsDAO {

	@Override
	public Key<Sanction> create(Sanction item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(Sanction item) throws Exception {
		boolean updateResult;
		if (item == null || item.getId() == null) {
			updateResult = false;
		} else {
			Sanction c = ofy().load().type(Sanction.class).id(item.getId()).now();
			if (c != null) {
				ofy().save().entity(item).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Sanction item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public Sanction findById(Long id) {
		return ofy().load().type(Sanction.class).id(id).now();
	}

	@Override
	public List<Sanction> findByCompetitionId(Long id) {
		return findByCompetitionIdAndTeamId(id, null);
	}

	@Override
	public List<Sanction> findByCompetitionIdAndTeamId(Long idCompetition, Long idTeam) {
		Query<Sanction> query = ofy().load().type(Sanction.class);
		if (idCompetition!=null) {
			Ref<Competition> ref = Ref.create(Key.create(Competition.class, idCompetition));
			query = query.filter("competitionRef", ref);
		}
		if (idTeam!=null) {
			Ref<Team> ref = Ref.create(Key.create(Team.class, idTeam));
			query = query.filter("teamRef", ref);
		}
		return query.list();

	}
}
