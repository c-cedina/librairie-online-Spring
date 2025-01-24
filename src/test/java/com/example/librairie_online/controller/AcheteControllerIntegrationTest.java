package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Achete;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.AcheteRepository;
import com.example.librairie_online.repository.ClientRepository;
import com.example.librairie_online.repository.MangaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testAchete")
public class AcheteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AcheteRepository acheteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @BeforeEach
    public void setup() {
        acheteRepository.deleteAll();
        clientRepository.deleteAll();
        mangaRepository.deleteAll();
    }

    @Test
    public void testCreateAchete() throws Exception {
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

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        String acheteJson = """
                {
                    "client": {"nadherent": %d},
                    "manga": {"nserie": %d},
                    "date": "2023-01-01",
                    "prix": 19.99
                }
                """.formatted(client.getNAdherent(), manga.getNserie());

        mockMvc.perform(post("/Achete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(acheteJson))
                .andExpect(status().isCreated());

        // Vérifiez le nombre d'instances de Achete créées
        long count = acheteRepository.count();
        assertThat(count).isEqualTo(1); // Changez la valeur attendue en fonction de votre configuration de test
    }

    @Test
    public void testReadAchetes() throws Exception {
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

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Achete achete = new Achete();
        achete.setClient(client);
        achete.setManga(manga);
        achete.setDate(LocalDate.of(2023, 1, 1));
        achete.setPrix(new BigDecimal("19.99"));
        acheteRepository.save(achete);

        mockMvc.perform(get("/Achete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].client.nadherent").value(client.getNAdherent()))
                .andExpect(jsonPath("$[0].manga.nserie").value(manga.getNserie()))
                .andExpect(jsonPath("$[0].date").value("2023-01-01"))
                .andExpect(jsonPath("$[0].prix").value(19.99));
    }

    @Test
    public void testUpdateAchete() throws Exception {
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

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Achete achete = new Achete();
        achete.setClient(client);
        achete.setManga(manga);
        achete.setDate(LocalDate.of(2023, 1, 1));
        achete.setPrix(new BigDecimal("19.99"));
        achete = acheteRepository.save(achete);

        String updatedAcheteJson = """
                {
                    "client": {"nadherent": %d},
                    "manga": {"nserie": %d},
                    "date": "2023-01-05",
                    "prix": 29.99
                }
                """.formatted(client.getNAdherent(), manga.getNserie());

        mockMvc.perform(put("/Achete/" + achete.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedAcheteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2023-01-05"))
                .andExpect(jsonPath("$.prix").value(29.99));
    }

    @Test
    public void testDeleteAchete() throws Exception {
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

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Achete achete = new Achete();
        achete.setClient(client);
        achete.setManga(manga);
        achete.setDate(LocalDate.of(2023, 1, 1));
        achete.setPrix(new BigDecimal("19.99"));
        achete = acheteRepository.save(achete);

        mockMvc.perform(delete("/Achete/" + achete.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/Achete/" + achete.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}