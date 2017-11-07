package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.User;

public interface UsersManager {
	List<User> queryAllUsers();
	User queryUserByName(String userName);
	void addUser(User user) throws Exception;
	void addUser(User user, Long townId) throws Exception;
	boolean removeUser(String userName) throws Exception;
	boolean updateUser(User user) throws Exception;
	void removeAll() throws Exception;
}
