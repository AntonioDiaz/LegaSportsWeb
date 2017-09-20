package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.User;
import com.googlecode.objectify.Key;

public interface UsersDAO extends GenericDAO<User> {
	Key<User> create(User user, Long townId) throws Exception;
	List<User> findAll();
	List<User> findByTown(Long idTown);
	User findUser(String userName);
}
