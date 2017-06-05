package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.adiaz.entities.UsersVO;
import com.googlecode.objectify.Key;

@Repository
public class UsersDAOImpl implements UsersDAO {

	@Override
	public Key<UsersVO> create(UsersVO item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(UsersVO usersVO) throws Exception {
		boolean updateResult = false;
		if (usersVO != null && usersVO.getUsername() != null) {
			UsersVO c = ofy().load().type(UsersVO.class).id(usersVO.getUsername()).now();
			if (c != null) {
				ofy().save().entity(usersVO).now();
				updateResult = true;
			} 
		}
		return updateResult;
	}

	@Override
	public boolean remove(UsersVO item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<UsersVO> findAllUsers() {
		return ofy().load().type(UsersVO.class).list();
	}

	@Override
	public UsersVO findUser(String userName) {
		Key<UsersVO> key = Key.create(UsersVO.class, userName);
		return ofy().load().key(key).now();
	}

}
