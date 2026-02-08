package com.invify.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invify.dto.APIResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        APIResponseDTO apiResponse = APIResponseDTO.builder()
                .success(false)
                .error("Access denied or missing token. You are not authorized to access this resource")
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }
}