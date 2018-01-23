package ru.tony.sample.configuration.filter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.tony.sample.database.entity.AuthToken;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.repository.AuthTokenRepository;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * sbt-dranitsyn-as
 * 23.01.2018
 */
public class TokenAuthenticationManagerTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private AuthTokenRepository authTokenRepository;
    private TokenAuthenticationManager sut;
    private UserDetailsService userDetailsService;


    @Before
    public void beforeMethod() {
        authTokenRepository = mock(AuthTokenRepository.class);
        userDetailsService = mock(UserDetailsService.class);
        sut = new TokenAuthenticationManager(authTokenRepository, userDetailsService);
    }

    @Test
    public void shouldFailWhenIncorrectToken() {
        thrown.expect(AuthenticationServiceException.class);
        thrown.expectMessage("Incorrect token");
        sut.authenticate(new TokenAuthentication(null));
    }

    @Test
    public void shouldFailWhenTokenIsExpired() {
        thrown.expect(AuthenticationServiceException.class);
        thrown.expectMessage("Invalid token");

        String token = "test-token";
        AuthToken value = new AuthToken();
        value.setExpireTime(new Date(System.currentTimeMillis() - 1000));
        when(authTokenRepository.findByToken(eq(token))).thenReturn(value);

        sut.authenticate(new TokenAuthentication(token));
    }

    @Test
    public void shouldReturnCorrectUserDetails() {
        String token = "test-token";
        String testName = "testName";
        Staff staff = new Staff();
        staff.setName(testName);
        AuthToken value = new AuthToken();
        value.setExpireTime(new Date(System.currentTimeMillis() + 1000));
        value.setStaff(staff);
        when(authTokenRepository.findByToken(eq(token))).thenReturn(value);
        when(userDetailsService.loadUserByUsername(eq(testName))).thenReturn(new User(testName, "pass", new ArrayList<GrantedAuthority>()));

        TokenAuthentication authentication = (TokenAuthentication) sut.authenticate(new TokenAuthentication(token));
        assertNotNull(authentication);
    }



}