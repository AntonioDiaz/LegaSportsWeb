package com.adiaz.services;

import com.adiaz.daos.ParametersDAO;
import com.adiaz.entities.Parameter;
import com.adiaz.utils.LocalSportsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("parametersManager")
public class ParametersManagerImpl implements ParametersManager {

    @Autowired
    ParametersDAO parametersDAO;


    @Override
    public List<Parameter> queryParameters() {
        return parametersDAO.findAll();
    }

    @Override
    public String getParameterFcmKeyServer() {
        String fcmKeyServer = "";
        List<Parameter> parameterList = parametersDAO.findByKey(LocalSportsConstants.PARAMETER_FCM_SERVER_KEY);
        if (parameterList.size()>0){
            fcmKeyServer = parameterList.get(0).getValue();
        }
        return fcmKeyServer;
    }

    @Override
    public Parameter queryById(Long id) {
        return parametersDAO.findById(id);
    }

    @Override
    public void add(Parameter parameter) throws Exception {
        parametersDAO.create(parameter);
    }

    @Override
    public boolean update(Parameter parameter) throws Exception {
        return parametersDAO.update(parameter);
    }

    @Override
    public boolean delete(Parameter parameter) throws Exception {
        return parametersDAO.remove(parameter);
    }
}
