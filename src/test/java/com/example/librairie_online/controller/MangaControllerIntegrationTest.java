package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.MangaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testManga")
public class MangaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MangaRepository mangaRepository;

    @BeforeEach
    public void setup() {
        mangaRepository.deleteAll();
    }

    @Test
    public void testCreateManga() throws Exception {
        String mangaJson = """
                {
                    "nserie": 93497,
                    "nom": "Naruto",
                    "date_parution": 2009,
                    "tome": 1,
                    "nbExemplaire": 6,
                    "mangaka": {
                        "id": {
                            "nom": "lou",
                            "prenom": "rock"
                        },
                        "sexe": "F",
                        "nationalite": "Française"
                    },
                    "fournisseur": {
                        "nom": "Manga World",
                        "ville": "Versailles"
                    }
                }
                """;
        mockMvc.perform(post("/Manga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mangaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Naruto"));
    }

    @Test
    public void testReadMangas() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setNserie(12345);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        manga.setDate_parution(2023);
        mangaRepository.save(manga);

        mockMvc.perform(get("/Manga")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nom").value("Naruto"));
    }

    @Test
    public void testReadMangaById() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setNserie(12345);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        manga.setDate_parution(2023);
        manga = mangaRepository.save(manga);

        mockMvc.perform(get("/Manga/" + manga.getNserie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Naruto"));
    }

    @Test
    public void testUpdateManga() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setNserie(12345);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        manga.setDate_parution(2023);
        manga = mangaRepository.save(manga);
        String updatedMangaJson = """
                {
                    "nserie": 12345,
                    "nom": "Naruto Shippuden",
                    "date_parution": 2023,
                    "tome": 2,
                    "nbExemplaire": 15,
                    "mangaka": {
                        "id": {
                            "nom": "tarzan",
                            "prenom": "dubois"
                        },
                        "sexe": "F",
                        "nationalite": "Française"
                    },
                    "fournisseur": {
                        "nom": "Manga World",
                        "ville": "Versailles"
                    }
                }
                """;

        mockMvc.perform(put("/Manga/" + manga.getNserie())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMangaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Naruto Shippuden"));
    }

    @Test
    public void testDeleteManga() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setNserie(12345);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        manga.setDate_parution(2023);
        manga = mangaRepository.save(manga);

        mockMvc.perform(delete("/Manga/" + manga.getNserie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<Manga> deletedManga = mangaRepository.findById(manga.getNserie());
        assert (deletedManga.isEmpty());
    }
}