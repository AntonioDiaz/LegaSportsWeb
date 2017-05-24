package com.adiaz.services.security;

import com.adiaz.entities.UsersVO;

public interface CurrentUserManager {

	public UsersVO getEnabledUser();
	
}
