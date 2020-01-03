package com.educo.educo.security;

import com.educo.educo.exceptions.GenericExceptionResponse;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        GenericExceptionResponse exceptionResponse = new GenericExceptionResponse("Unauthorized");
        String jsonExceptionResponse = new Gson().toJson(exceptionResponse);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonExceptionResponse);
    }
}
