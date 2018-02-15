package com.sysco.app.configuration;

import com.sysco.app.exception.AuthenticationFailureException;
import com.sysco.app.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityKeyInterceptor implements HandlerInterceptor {

    @Value("${API_KEY}")
    private String apiKey;

    private static final
    Logger LOGGER = LoggerFactory.getLogger(SecurityKeyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {
            if (!request.getHeader("APIKEY").contentEquals(apiKey)) {
                LOGGER.error(ErrorCode.AUTHENTICATION_FAILURE.getDesc());
                throw new AuthenticationFailureException(this.getClass().getName(), ErrorCode.AUTHENTICATION_FAILURE);
            } else {
                return true;
            }
        } catch (NullPointerException e) {
            LOGGER.error(ErrorCode.MISSING_AUTHENTICATION_KEY.getDesc());
            throw new AuthenticationFailureException(this.getClass().getName(), ErrorCode.MISSING_AUTHENTICATION_KEY);
        }
    }
}
