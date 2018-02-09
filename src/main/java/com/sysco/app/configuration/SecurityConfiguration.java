package com.sysco.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static String REALM="MY_TEST_REALM";

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) {
        try {
            auth.inMemoryAuthentication().withUser("james").password("123123").roles("ADMIN");
        } catch (Exception e) {

        }
    }
}
