package com.sysco.app.configuration;

import com.sysco.app.configuration.security.CustomBasicAuthenticationEntryPoint;
import com.sysco.app.exception.AuthorizationFailureException;
import com.sysco.app.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static String REALM="MY_TEST_REALM";

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) {
        try {
            auth.inMemoryAuthentication().withUser("james").password("123123").roles("ADMIN");
            auth.inMemoryAuthentication().withUser("tom").password("123123").roles("USER");
        } catch (Exception e) {
            String errorMessage = "SecurityConfiguration.configureGlobalSecurity: Invalid access";
            throw new AuthorizationFailureException(errorMessage, ErrorCode.AUTHENTICATION_FAILURE,
                    SecurityConfiguration.class);
        }
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity.csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/items/**")
                    .hasRole("ADMIN")
                    .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } catch (Exception e) {
            String errorMessage = "SecurityConfiguration.configureGlobalSecurity: Invalid access";
            throw new AuthorizationFailureException(errorMessage, ErrorCode.AUTHENTICATION_FAILURE,
                    SecurityConfiguration.class);
        }
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

}
