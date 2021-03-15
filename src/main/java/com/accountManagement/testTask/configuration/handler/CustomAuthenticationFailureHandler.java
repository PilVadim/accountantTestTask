package com.accountManagement.testTask.configuration.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final static String BLOCKED = "blocked";

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (BLOCKED.equals(e.getMessage())){
            httpServletResponse.setStatus(HttpStatus.LOCKED.value());
        } else {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        httpServletResponse.getOutputStream().println(String.format(jsonPayload, e.getMessage(), LocalTime.now()));
    }
}