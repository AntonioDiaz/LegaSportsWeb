package com.adiaz.daos;

import com.adiaz.entities.Competition;
import com.adiaz.entities.Issue;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by toni on 14/09/2017.
 */

@Repository
public class IssuesDAOImpl implements IssuesDAO {

    @Override
    public Key<Issue> create(Issue item) throws Exception {
        return ofy().save().entity(item).now();
    }

    @Override
    public boolean update(Issue item) throws Exception {
        boolean updateResult;
        if (item == null || item.getId() == null) {
            updateResult = false;
        } else {
            Issue c = ofy().load().type(Issue.class).id(item.getId()).now();
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
    public boolean remove(Issue item) throws Exception {
        ofy().delete().entity(item).now();
        return true;
    }

    @Override
    public List<Issue> findByCompetition(Long competitionId) {
        List<Issue> issues = null;
        Query<Issue> query = ofy().load().type(Issue.class);
        if (competitionId != null) {
            Key<Competition> key = Key.create(Competition.class, competitionId);
            issues = query
                    .filter("competitionRef", Ref.create(key))
                    .order("-dateSent").list();
        }
        return issues;
    }

    @Override
    public List<Issue> findByTown(Long townId) {
        List<Issue> issues = null;
        Query<Issue> query = ofy().load().type(Issue.class);
        if (townId != null) {
            Key<Town> key = Key.create(Town.class, townId);
            issues = query
                    .filter("townRef", Ref.create(key))
                    .order("-dateSent").list();
        }
        return issues;
    }

    @Override
    public List<Issue> findByClientIdInPeriod(String clientId, Date dateFrom, Date dateTo) {
        List<Issue> issues = null;
        Query<Issue> query = ofy().load().type(Issue.class);
        if (clientId != null && dateFrom != null && dateTo != null) {
            issues = query
                    .filter("clientId", clientId)
                    .filter("dateSent >", dateFrom)
                    .filter("dateSent <", dateTo)
                    .list();
        }
        return issues;
    }

    @Override
    public List<Issue> findInPeriod(Date dateFrom, Date dateTo) {
        List<Issue> issues = null;
        Query<Issue> query = ofy().load().type(Issue.class);
        if (dateFrom != null && dateTo != null) {
            issues = query
                    .filter("dateSent >", dateFrom)
                    .filter("dateSent <", dateTo)
                    .list();
        }
        return issues;
    }

    @Override
    public List<Issue> findAll() {
        return ofy().load().type(Issue.class).list();
    }

    @Override
    public Issue findById(Long id) {
        return ofy().load().type(Issue.class).id(id).now();
    }
}
