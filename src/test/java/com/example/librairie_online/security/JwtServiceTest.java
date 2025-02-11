package com.example.librairie_online.security;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Role;
import com.example.librairie_online.enumeration.TypeRole;
import com.example.librairie_online.repository.JwtRepository;
import com.example.librairie_online.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private JwtRepository jwtRepository;

    @InjectMocks
    private JwtService jwtService;




    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService(clientService, jwtRepository);
        ReflectionTestUtils.setField(jwtService, "encryptionKey", Base64.getEncoder().encodeToString("ca666f0d0bf6cae5d937cfe81dd3e036ccf9f38d176d78865f129bd10ad48bb2".getBytes()));

    }

    @Test
    public void testGenerate() {
        String email = "test@example.com";
        Client client = new Client();
        client.setEmail(email);
        client.setNom("Test");
        client.setPrenom("User");
        Role role = new Role();
        role.setRole(TypeRole.USER);
        client.setRole(role);

        when(clientService.loadUserByUsername(email)).thenReturn(client);
        when(jwtRepository.findAllByEmail(email)).thenReturn(List.of());

        Map<String, String> jwtMap = jwtService.generate(email);

        assertThat(jwtMap).isNotNull();
        assertThat(jwtMap.get(JwtService.BEARER)).isNotEmpty();

        verify(jwtRepository, times(1)).save(any());
    }
}