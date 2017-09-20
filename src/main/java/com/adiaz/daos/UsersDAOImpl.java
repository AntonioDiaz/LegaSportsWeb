package com.adiaz.daos;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.adiaz.entities.Town;
import com.adiaz.entities.User;
import com.googlecode.objectify.Ref;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;

@Repository
public class UsersDAOImpl implements UsersDAO {

	@Override
	public Key<User> create(User item) throws Exception {
		return ofy().save().entity(item).now();
	}

	@Override
	public boolean update(User user) throws Exception {
		boolean updateResult = false;
		if (user != null && user.getUsername() != null) {
			User c = ofy().load().type(User.class).id(user.getUsername()).now();
			if (c != null) {
				ofy().save().entity(user).now();
				updateResult = true;
			} 
		}
		return updateResult;
	}

	@Override
	public boolean remove(User item) throws Exception {
		ofy().delete().entity(item).now();
		return true;
	}

	@Override
	public List<User> findAllUsers() {
		return ofy().load().type(User.class).list();
	}

	@Override
	public List<User> findByTown(Long idTown) {
		Key<Town> key = Key.create(Town.class, idTown);
		Ref<Town> townRef = Ref.create(key);
		return ofy().load().type(User.class).filter("townRef", townRef).list();
	}

	@Override
	public User findUser(String userName) {
		Key<User> key = Key.create(User.class, userName);
		return ofy().load().key(key).now();
	}

	@Override
	public Key<User> create(User user, Long townId) throws Exception {
		Key<Town> townKey = Key.create(Town.class, townId);
		Ref<Town> townRef = Ref.create(townKey);
		user.setTownRef(townRef);
		return this.create(user);
	}

}
