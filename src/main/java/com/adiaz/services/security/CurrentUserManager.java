package com.adiaz.services.security;

import com.adiaz.entities.User;

public interface CurrentUserManager {

	public User getEnabledUser();
	
}
