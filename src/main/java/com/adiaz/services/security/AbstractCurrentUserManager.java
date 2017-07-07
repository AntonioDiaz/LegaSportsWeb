package com.adiaz.services.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.adiaz.entities.UsersVO;

public abstract class AbstractCurrentUserManager implements CurrentUserManager {	
	@Override
	public UsersVO getEnabledUser() {
		UsersVO principal = (UsersVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal;
	}
}