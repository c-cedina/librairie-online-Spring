package com.example.librairie_online.security;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Jwt;
import com.example.librairie_online.entity.Role;
import com.example.librairie_online.enumeration.TypeRole;
import com.example.librairie_online.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JwtFilterTest {

    @Mock
    private ClientService clientService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtFilter = new JwtFilter(clientService, jwtService);
    }

    @Test
    public void testDoFilterInternal() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer testToken");

        Jwt jwt = new Jwt();
        Client client = new Client();
        client.setEmail("test@example.com");
        Role role = new Role();
        role.setRole(TypeRole.USER);
        client.setRole(role);
        jwt.setClient(client);

        when(jwtService.readByToken("testToken")).thenReturn(jwt);
        when(jwtService.isTokenExpired("testToken")).thenReturn(false);
        when(jwtService.extractUsername("testToken")).thenReturn("test@example.com");

        UserDetails userDetails = mock(UserDetails.class);
        when(clientService.loadUserByUsername("test@example.com")).thenReturn(userDetails);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).readByToken("testToken");
        verify(jwtService, times(1)).isTokenExpired("testToken");
        verify(jwtService, times(1)).extractUsername("testToken");
        verify(clientService, times(1)).loadUserByUsername("test@example.com");
        verify(filterChain, times(1)).doFilter(request, response);

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(userDetails);
    }
}