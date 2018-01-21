package ru.tony.sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public Http401AuthenticationEntryPoint securityException401EntryPoint(){
        return new Http401AuthenticationEntryPoint("");
    }

    @Autowired
    private Http401AuthenticationEntryPoint authEntrypoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").anonymous()
                .antMatchers("/api/staff/*").authenticated()
                .antMatchers("/api/audit/*").authenticated()
            .and()
                .formLogin()
                .loginPage("/api/auth")
                .permitAll()
            /*.and()
                .csrf()
                .disable()
                .headers()*/
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .logout()
                .permitAll()
            .and()
                .exceptionHandling().authenticationEntryPoint(authEntrypoint);
    }
}
