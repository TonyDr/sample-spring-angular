package ru.tony.sample.configuration.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.entity.StaffRole;
import ru.tony.sample.database.repository.StaffRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * sbt-dranitsyn-as
 * 24.01.2018
 */
@Service
public class StaffUserDetailsService implements UserDetailsService {

    private StaffRepository staffRepository;

    @Autowired
    public StaffUserDetailsService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByName(name);
        return new StaffPrincipal(staff.getName(), staff.getPassword(), getGrantedAuthorities(staff.getRole()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(StaffRole role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }
}
