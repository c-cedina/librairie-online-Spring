package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.entity.Studio;
import com.example.librairie_online.repository.AnimeRepository;
import com.example.librairie_online.repository.MangaRepository;
import com.example.librairie_online.repository.StudioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testAnime")
public class AnimeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private StudioRepository studioRepository;

    @BeforeEach
    public void setup() {
        animeRepository.deleteAll();
        mangaRepository.deleteAll();
        studioRepository.deleteAll();
    }

    @Test
    public void testCreateAnime() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDate_parution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Studio studio = new Studio();
        studio.setNom("Studio Pierrot");
        studio.setPays("Japan");
        studioRepository.save(studio);

        String animeJson = """
                {
                    "nom": "Naruto",
                    "date": 2002,
                    "manga": {
                        "nserie": """ + manga.getNserie() + """
                    },
                    "studio": {
                        "nom": "Studio Pierrot"
                    }
                }
                """;

        mockMvc.perform(post("/Anime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(animeJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testReadAnimes() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDate_parution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Studio studio = new Studio();
        studio.setNom("Studio Pierrot");
        studio.setPays("Japan");
        studioRepository.save(studio);

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        anime.setManga(manga);
        anime.setStudio(studio);
        animeRepository.save(anime);

        mockMvc.perform(get("/Anime")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Naruto"))
                .andExpect(jsonPath("$[0].date").value(2002));
    }

    @Test
    public void testUpdateAnime() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDate_parution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Studio studio = new Studio();
        studio.setNom("Studio Pierrot");
        studio.setPays("Japan");
        studioRepository.save(studio);

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        anime.setManga(manga);
        anime.setStudio(studio);
        animeRepository.save(anime);

        String updatedAnimeJson = """
                {
                    "nom": "Naruto Shippuden",
                    "date": 2007,
                    "manga": {
                        "nserie": """ + manga.getNserie() + """
                    },
                    "studio": {
                        "nom": "Studio Pierrot"
                    }
                }
                """;

        mockMvc.perform(put("/Anime/" + anime.getNSerie())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedAnimeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Naruto Shippuden"))
                .andExpect(jsonPath("$.date").value(2007));
    }

    @Test
    public void testDeleteAnime() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDate_parution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Studio studio = new Studio();
        studio.setNom("Studio Pierrot");
        studio.setPays("Japan");
        studioRepository.save(studio);

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        anime.setManga(manga);
        anime.setStudio(studio);
        animeRepository.save(anime);

        mockMvc.perform(delete("/Anime/" + anime.getNSerie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/Anime/" + anime.getNSerie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}