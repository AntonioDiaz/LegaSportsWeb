package com.adiaz.daos;

import com.adiaz.entities.Parameter;
import com.adiaz.entities.Town;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Repository
public class ParametersDAOImpl implements ParametersDAO {

    @Override
    public List<Parameter> findAll() {
        Query<Parameter> query = ofy().load().type(Parameter.class);
        return query.list();
    }

    @Override
    public Parameter findById(Long idCompetition) {
        return ofy().load().type(Parameter.class).id(idCompetition).now();
    }

    @Override
    public List<Parameter> findByKey(String key) {
        return ofy().load().type(Parameter.class).filter("key", key).list();
    }

    @Override
    public Key<Parameter> create(Parameter parameter) throws Exception {
        return ofy().save().entity(parameter).now();
    }

    @Override
    public boolean update(Parameter parameter) throws Exception {
        boolean updateResult = false;
        if (parameter != null && parameter.getId() != null) {
            updateResult = true;
            ofy().save().entity(parameter).now();
        }
        return updateResult;
    }

    @Override
    public boolean remove(Parameter item) throws Exception {
        ofy().delete().entity(item).now();
        return true;
    }
}
