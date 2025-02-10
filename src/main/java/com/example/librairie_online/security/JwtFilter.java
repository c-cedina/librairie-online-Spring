package com.example.librairie_online.security;

import com.example.librairie_online.service.ClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {

    private ClientService clientService;
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email = null;
        String token = null;
        boolean isTokenExpired = true;
        final String authorization = request.getHeader("Authorization");


        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            isTokenExpired = jwtService.isTokenExpired(token);
            email = jwtService.extractUsername(token);
        }

        if (!isTokenExpired && email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.clientService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

