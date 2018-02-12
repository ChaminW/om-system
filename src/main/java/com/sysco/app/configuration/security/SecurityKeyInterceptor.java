package com.sysco.app.configuration.security;

import com.sysco.app.exception.AuthenticationFailureException;
import com.sysco.app.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityKeyInterceptor implements HandlerInterceptor {

    @Value("${API_KEY}")
    private String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!request.getHeader("APIKEY").contentEquals(apiKey)) {
            throw new AuthenticationFailureException("Authentication Failure",
                    ErrorCode.AUTHENTICATION_FAILURE, SecurityKeyInterceptor.class);
        }
        return true;
    }
}
