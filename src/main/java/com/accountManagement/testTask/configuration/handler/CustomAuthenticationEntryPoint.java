package com.accountManagement.testTask.configuration.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(String.format(jsonPayload, authException.getMessage(), LocalTime.now()));
    }
}

