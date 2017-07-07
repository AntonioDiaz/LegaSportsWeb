package com.adiaz.services.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adiaz.entities.UsersVO;
import com.adiaz.services.UsersManager;

@Service ("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	Logger logger = Logger.getLogger(CustomUserDetailsService.class);
	
	@Autowired UsersManager usersManager;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("username: " + username);
		UsersVO user = usersManager.queryUserByName(username); 
//		/** should find user by "username". */ 
//		UsersVO user = new UsersVO();
//		user.setUsername("adiaz");
//		user.setEnabled(true);
//		user.setBannedUser(false);
//		user.setAccountNonExpired(true);
//		/** password: admin*/
//		user.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
//		user.setAdmin(true);
		if (user==null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
}