package com.ecom.zestcart.config;

import com.ecom.zestcart.service.JWTService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Collection;

public class JWTFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader("Authorization");

        boolean isOpenApiRequest = httpRequest.getRequestURI().startsWith("/api/open") || httpRequest.getRequestURI().startsWith("/auth") || httpRequest.getRequestURI().startsWith("/fileUploads");

        if (isOpenApiRequest) {
            chain.doFilter(request, response);
            return;
        }

        // Token validation logic for other requests
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                if (jwtService.validateToken(token)) {
                    String username = jwtService.getUsernameFromToken(token);
                    Collection<? extends GrantedAuthority> authorities = jwtService.getAuthoritiesFromToken(token);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    sendUnauthorizedResponse(httpResponse, "Invalid token. Please log in again.");
                    return;
                }
            } catch (Exception e) {
                sendUnauthorizedResponse(httpResponse, "Token validation failed: " + e.getMessage());
                return;
            }
        } else {
            sendUnauthorizedResponse(httpResponse, "No token found. Please log in again.");
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
