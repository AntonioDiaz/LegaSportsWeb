package com.adiaz.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

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

    @Load
    @Index
    private Ref<Town> townRef;

    @Ignore
    private Town townEntity;

    @OnLoad
    public void getRefs() {
        if (townRef != null && townRef.isLoaded()) {
            townEntity = townRef.get();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (admin) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorityList;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !bannedUser;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return accountNonExpired;
    }

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

    public Ref<Town> getTownRef() {
        return townRef;
    }

    public void setTownRef(Ref<Town> townRef) {
        this.townRef = townRef;
    }

    public Town getTownEntity() {
        return townEntity;
    }

    public void setTownEntity(Town townEntity) {
        this.townEntity = townEntity;
    }
}
