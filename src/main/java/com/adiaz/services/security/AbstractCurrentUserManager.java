package com.adiaz.services.security;

import com.adiaz.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractCurrentUserManager implements CurrentUserManager {	
	@Override
	public User getEnabledUser() {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal;
	}
}