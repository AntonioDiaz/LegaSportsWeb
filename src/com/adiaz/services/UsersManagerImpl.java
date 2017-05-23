package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.UsersDAO;
import com.adiaz.entities.UsersVO;

@Service ("usersManager")
public class UsersManagerImpl implements UsersManager {

	@Autowired
	UsersDAO usersDAO;
	
	@Override
	public List<UsersVO> queryAllUsers() {		
		return usersDAO.findAllUsers();
	}

	@Override
	public UsersVO queryUserByName(String userName) {
		return usersDAO.findUser(userName);
	}

	@Override
	public void addUser(UsersVO usersVO) throws Exception {
		usersDAO.create(usersVO);
	}

	@Override
	public boolean removeUser(String userName) throws Exception {
		boolean userDeleted = false;
		UsersVO usersVO = usersDAO.findUser(userName);		
		if (usersVO!=null) {
			userDeleted = usersDAO.remove(usersVO);
		}
		return userDeleted;
	}

	@Override
	public boolean updateUser(UsersVO userVO) throws Exception{		
		return usersDAO.update(userVO);
	}

}
