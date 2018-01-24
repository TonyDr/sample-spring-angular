package ru.tony.sample.configuration.filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.entity.StaffRole;
import ru.tony.sample.database.repository.StaffRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.tony.sample.database.entity.StaffRole.USER;

/**
 *
 * 23.01.2018
 */
public class StaffUserDetailsServiceTest {


    private UserDetailsService sut;
    private StaffRepository staffRepository;

    @Before
    public void beforeTest() {
        staffRepository = mock(StaffRepository.class);
        sut = new StaffUserDetailsService(staffRepository);
    }

    @Test
    public void shouldReturnCorrectUserDetails() {
        String name = "testName";
        String password = "testPass";
        Staff staff = new Staff();
        staff.setName(name);
        staff.setPassword(password);
        staff.setRole(USER);
        when(staffRepository.findByName(eq(name))).thenReturn(staff);

        UserDetails user =  sut.loadUserByUsername(name);
        assertNotNull(user);
        assertEquals(name, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(USER.toString())));
        verify(staffRepository).findByName(eq(name));
    }
}
