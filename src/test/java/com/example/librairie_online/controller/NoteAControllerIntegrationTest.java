package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.NoteA;
import com.example.librairie_online.repository.AnimeRepository;
import com.example.librairie_online.repository.ClientRepository;
import com.example.librairie_online.repository.NoteARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testNoteA")
public class NoteAControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteARepository noteARepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AnimeRepository animeRepository;

    @BeforeEach
    public void setup() {
        noteARepository.deleteAll();
        clientRepository.deleteAll();
        animeRepository.deleteAll();
    }

    @Test
    public void testCreateNoteA() throws Exception {
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

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        animeRepository.save(anime);

        String noteAJson = """
                {
                    "nadherent": %d,
                    "nserie": %d,
                    "valeur": 4.5,
                    "date": "2023-01-01"
                }
                """.formatted(client.getNAdherent(), anime.getNSerie());

        mockMvc.perform(post("/Note/Anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteAJson))
                .andExpect(status().isCreated());

        // Vérifiez le nombre d'instances de NoteA créées
        long count = noteARepository.count();
        assertThat(count).isEqualTo(1); // Changez la valeur attendue en fonction de votre configuration de test
    }

    @Test
    public void testReadNoteAs() throws Exception {
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

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        animeRepository.save(anime);

        NoteA noteA = new NoteA();
        noteA.setNadherent(client.getNAdherent());
        noteA.setNSerie(anime.getNSerie());
        noteA.setValeur(4.5);
        noteA.setDate(LocalDate.of(2023, 1, 1));
        noteARepository.save(noteA);

        mockMvc.perform(get("/Note/Anime")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nadherent").value(client.getNAdherent()))
                .andExpect(jsonPath("$[0].nserie").value(anime.getNSerie()))
                .andExpect(jsonPath("$[0].valeur").value(4.5))
                .andExpect(jsonPath("$[0].date").value("2023-01-01"));
    }

    @Test
    public void testUpdateNoteA() throws Exception {
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

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        animeRepository.save(anime);

        NoteA noteA = new NoteA();
        noteA.setNadherent(client.getNAdherent());
        noteA.setNSerie(anime.getNSerie());
        noteA.setValeur(4.5);
        noteA.setDate(LocalDate.of(2023, 1, 1));
        noteA = noteARepository.save(noteA);

        String updatedNoteAJson = """
                {
                    "nadherent": %d,
                    "nSerie": %d,
                    "valeur": 3.5,
                    "date": "2023-01-05"
                }
                """.formatted(client.getNAdherent(), anime.getNSerie());

        mockMvc.perform(put("/Note/Anime/" + client.getNAdherent() + "/" + anime.getNSerie())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedNoteAJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valeur").value(3.5))
                .andExpect(jsonPath("$.date").value("2023-01-05"));
    }

    @Test
    public void testDeleteNoteA() throws Exception {
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

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        animeRepository.save(anime);

        NoteA noteA = new NoteA();
        noteA.setNadherent(client.getNAdherent());
        noteA.setNSerie(anime.getNSerie());
        noteA.setValeur(4.5);
        noteA.setDate(LocalDate.of(2023, 1, 1));
        noteA = noteARepository.save(noteA);

        mockMvc.perform(delete("/Note/Anime/" + client.getNAdherent() + "/" + anime.getNSerie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/Note/Anime/" + client.getNAdherent() + "/" + anime.getNSerie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
