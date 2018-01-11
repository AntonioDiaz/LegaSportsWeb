package com.adiaz.services;

import com.adiaz.entities.Parameter;

import java.util.List;

public interface ParametersManager {
    List<Parameter> queryParameters();
    Parameter queryById(Long id);
    String getParameterFcmKeyServer();
    void add (Parameter parameter) throws Exception;
    boolean update(Parameter parameter) throws Exception;
    boolean delete(Parameter parameter) throws Exception;

}
