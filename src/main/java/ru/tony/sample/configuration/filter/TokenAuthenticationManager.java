package ru.tony.sample.configuration.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.tony.sample.database.entity.AuthToken;
import ru.tony.sample.database.repository.AuthTokenRepository;

import java.util.Date;

/**
 * sbt-dranitsyn-as
 * 23.01.2018
 */
@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    private AuthTokenRepository authTokenRepository;
    private UserDetailsService userDetailsService;

    @Autowired
    public TokenAuthenticationManager(AuthTokenRepository authTokenRepository, UserDetailsService userDetailsService) {
        this.authTokenRepository = authTokenRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof TokenAuthentication) {
            return processAuthentication((TokenAuthentication) authentication);
        } else {
            authentication.setAuthenticated(false);
            return authentication;
        }
    }

    private TokenAuthentication processAuthentication(TokenAuthentication authentication) {

        AuthToken token = authTokenRepository.findByToken(authentication.getToken());
        if (token == null) {
            throw new AuthenticationServiceException("Incorrect token");
        }
        if (token.getExpireTime().before(new Date())) {
            throw new AuthenticationServiceException("Invalid token");
        }

        return buildFullTokenAuthentication(token);
    }

    private TokenAuthentication buildFullTokenAuthentication(AuthToken token) {
        User user = (User) userDetailsService.loadUserByUsername(token.getStaff().getName());
        return new TokenAuthentication(token.getToken(), user, true, user.getAuthorities());
    }
}
