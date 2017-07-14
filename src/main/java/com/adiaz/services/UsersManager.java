package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.User;

public interface UsersManager {

	public List<User> queryAllUsers();
	public User queryUserByName(String userName);
	public void addUser(User user) throws Exception;
	public boolean removeUser(String userName) throws Exception;
	public boolean updateUser(User user) throws Exception;
	public void removeAll() throws Exception;
	
}
