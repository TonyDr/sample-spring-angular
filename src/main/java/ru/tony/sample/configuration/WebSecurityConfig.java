package ru.tony.sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.tony.sample.configuration.filter.TokenAuthenticationFilter;
import ru.tony.sample.configuration.filter.TokenAuthenticationManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Http401AuthenticationEntryPoint authEntrypoint;

    @Autowired
    private TokenAuthenticationManager tokenAuthenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").anonymous()
                .antMatchers("/login").permitAll()
                .antMatchers("/rest/staff/*").hasRole("ADMIN")
                .antMatchers("/rest/audit/*").hasAnyRole("ADMIN", "USER")
            .and()
                .csrf()
                .disable()
                .headers()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .logout()
                .permitAll()

            .and()
                .exceptionHandling().authenticationEntryPoint(authEntrypoint);
    }

    @Bean
    public TokenAuthenticationFilter tokenFilter() {
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter();
        filter.setAuthenticationManager(tokenAuthenticationManager);
        return filter;
    }

    @Bean
    public Http401AuthenticationEntryPoint securityException401EntryPoint(){
        return new Http401AuthenticationEntryPoint("");
    }
}
