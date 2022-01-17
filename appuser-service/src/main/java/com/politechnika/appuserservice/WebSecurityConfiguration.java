package com.politechnika.appuserservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.annotation.Resource;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource(name="myAppUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("user1")).roles("USER");

        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin1")).roles("ADMIN");*/

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
