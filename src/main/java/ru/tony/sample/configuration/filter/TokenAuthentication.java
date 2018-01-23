package ru.tony.sample.configuration.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * sbt-dranitsyn-as
 * 23.01.2018
 */
public class TokenAuthentication implements Authentication {

    private String token;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isAuthenticated;
    private UserDetails principal;

    TokenAuthentication(String token) {
        this.token = token;
    }

    TokenAuthentication(String token, User user, boolean authorized, Collection<GrantedAuthority> authorities) {
        this.token = token;
        this.principal = user;
        this.isAuthenticated = authorized;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        isAuthenticated = authenticated;
    }

    @Override
    public String getName() {
        if (principal != null)
            return principal.getUsername();
        else
            return null;
    }

    public String getToken() {
        return token;
    }
}
