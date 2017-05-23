package com.adiaz.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;

@Entity
public class UsersVO {

	@Id
	private String username;

	private String password;
	
	@Ignore
	private String password01;
	
	@Ignore
	private String password02;

	@Ignore
	private boolean updatePassword;
	
	private boolean admin;

	private boolean enabled;

	private boolean bannedUser;

	private boolean accountNonExpired;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isBannedUser() {
		return bannedUser;
	}

	public void setBannedUser(boolean bannedUser) {
		this.bannedUser = bannedUser;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public String getPassword01() {
		return password01;
	}

	public void setPassword01(String password01) {
		this.password01 = password01;
	}

	public String getPassword02() {
		return password02;
	}

	public void setPassword02(String password02) {
		this.password02 = password02;
	}

	public boolean isUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(boolean updatePassword) {
		this.updatePassword = updatePassword;
	}
}
