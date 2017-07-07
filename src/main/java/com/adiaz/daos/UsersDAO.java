package com.adiaz.daos;

import java.util.List;

import com.adiaz.entities.UsersVO;

public interface UsersDAO extends GenericDAO<UsersVO> {
	
	public List<UsersVO> findAllUsers();
	public UsersVO findUser(String userName);

}
