package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.User;

public interface UsersDAO extends GenericDAO<User> {
	public List<User> findAllUsers();
	public User findUser(String userName);
}
