package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.User;
import com.googlecode.objectify.Key;

public interface UsersDAO extends GenericDAO<User> {
	public Key<User> create(User user, Long townId) throws Exception;
	public List<User> findAllUsers();
	public User findUser(String userName);
}
