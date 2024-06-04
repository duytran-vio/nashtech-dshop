package com.nashtech.dshop_api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler{

    private final HandlerExceptionResolver handleExceptionResolver;

    public CustomerAccessDeniedHandler(@Qualifier("handlerExceptionResolver")HandlerExceptionResolver handleExceptionResolver) {
        this.handleExceptionResolver = handleExceptionResolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.handleExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
    
}
