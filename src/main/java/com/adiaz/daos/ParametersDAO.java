package com.adiaz.daos;

import com.adiaz.entities.Parameter;

import java.util.List;

public interface ParametersDAO extends GenericDAO<Parameter> {

    List<Parameter> findAll();
    Parameter findById(Long id);
    List<Parameter> findByKey(String key);
}
