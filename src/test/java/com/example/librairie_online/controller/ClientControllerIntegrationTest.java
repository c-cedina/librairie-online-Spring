package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void setup() {
        clientRepository.deleteAll();
    }

    @Test
    public void testCreateClient() throws Exception {
        String clientJson = """
                {
                    "nom": "Doe",
                    "prenom": "John",
                    "sexe": "M",
                    "age": 30,
                    "date_naissance": "1991-01-01",
                    "date_adhesion": "2021-01-01",
                    "email": "Doe.Jonh@exemple.com",
                    "password": "password"
                }
                """;

        mockMvc.perform(post("/Client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testReadClients() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        client.setEmail("Doe.John@exemple.com");
        client.setPassword("password");
        clientRepository.save(client);

        mockMvc.perform(get("/Client")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Doe"))
                .andExpect(jsonPath("$[0].prenom").value("John"))
                .andExpect(jsonPath("$[0].sexe").value("M"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].date_naissance").value("1991-01-01"))
                .andExpect(jsonPath("$[0].date_adhesion").value("2021-01-01"))
                .andExpect(jsonPath("$[0].email").value("Doe.John@exemple.com"))
                .andExpect(jsonPath("$[0].password").value("password"));
    }

    @Test
    public void testUpdateClient() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        client.setEmail("Doe.John@exemple.com");
        client.setPassword("password");
        client = clientRepository.save(client);

        String updatedClientJson = """
                {
                    "nom": "Doe",
                    "prenom": "Jane",
                    "sexe": "F",
                    "age": 28,
                    "date_naissance": "1993-01-01",
                    "date_adhesion": "2021-01-01",
                    "email": "Doe.John_93@exememple.com",
                    "password": "pass"
                }
                """;

        mockMvc.perform(put("/Client/" + client.getNAdherent())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedClientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom").value("Jane"))
                .andExpect(jsonPath("$.sexe").value("F"))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.date_naissance").value("1993-01-01"));
    }

    @Test
    public void testDeleteClient() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        client = clientRepository.save(client);

        mockMvc.perform(delete("/Client/" + client.getNAdherent())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/Client/" + client.getNAdherent())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}