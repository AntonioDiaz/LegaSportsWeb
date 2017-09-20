package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.UsersDAO;

@Service ("usersManager")
public class UsersManagerImpl implements UsersManager {

	@Autowired
	UsersDAO usersDAO;
	
	@Override
	public List<User> queryAllUsers() {
		return usersDAO.findAll();
	}

	@Override
	public User queryUserByName(String userName) {
		return usersDAO.findUser(userName);
	}

	@Override
	public void addUser(User user) throws Exception {
		usersDAO.create(user);
	}

	@Override
	public void addUser(User user, Long townId) throws Exception {
		usersDAO.create(user, townId);
	}

	@Override
	public boolean removeUser(String userName) throws Exception {
		boolean userDeleted = false;
		User user = usersDAO.findUser(userName);
		if (user !=null) {
			userDeleted = usersDAO.remove(user);
		}
		return userDeleted;
	}

	@Override
	public boolean updateUser(User user) throws Exception{
		return usersDAO.update(user);
	}

	@Override
	public void removeAll() throws Exception {
		List<User> users = usersDAO.findAll();
		for (User user : users) {
			usersDAO.remove(user);
		}
	}

}
