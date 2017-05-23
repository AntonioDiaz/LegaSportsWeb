package com.adiaz.services;

import java.util.List;

import com.adiaz.entities.UsersVO;

public interface UsersManager {

	public List<UsersVO> queryAllUsers();
	public UsersVO queryUserByName(String userName);
	public void addUser(UsersVO usersVO) throws Exception;
	public boolean removeUser(String userName) throws Exception;
	public boolean updateUser(UsersVO userVO) throws Exception;
	
}
