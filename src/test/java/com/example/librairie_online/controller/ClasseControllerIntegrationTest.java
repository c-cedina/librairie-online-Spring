package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Classe;
import com.example.librairie_online.entity.Genre;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.ClasseRepository;
import com.example.librairie_online.repository.GenreRepository;
import com.example.librairie_online.repository.MangaRepository;

import com.example.librairie_online.security.TestSecurityConfig;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Import(TestSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClasseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    @BeforeEach
    public void setup() {
        classeRepository.deleteAll();
        mangaRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    public void testCreateClasse() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Genre genre = new Genre();
        genre.setType("Action");
        genreRepository.save(genre);

        String classeJson = """
                {
                    "nserie": %d,
                    "type": "Action"
                }
                """.formatted(manga.getNserie());
        System.out.println("JSON envoyé = " + classeJson);
        mockMvc.perform(post("/Classe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(classeJson))
                .andExpect(status().isCreated());

        // Vérifiez le nombre d'instances de Classe créées
        long count = classeRepository.count();
        assertThat(count).isEqualTo(1); // Changez la valeur attendue en fonction de votre configuration de test
    }

    @Test
    public void testReadClasses() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Genre genre = new Genre();
        genre.setType("Action");
        genreRepository.save(genre);

        Classe classe = new Classe();
        classe.setNSerie(manga.getNserie());
        classe.setType("Action");
        classe.setManga(manga);
        classe.setGenre(genre);
        classeRepository.save(classe);

        mockMvc.perform(get("/Classe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nserie").value(manga.getNserie()))
                .andExpect(jsonPath("$[0].type").value("Action"));
    }

    @Test
    public void testDeleteClasse() throws Exception {
        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        Genre genre = new Genre();
        genre.setType("Action");
        genreRepository.save(genre);

        Classe classe = new Classe();
        classe.setNSerie(manga.getNserie());
        classe.setType("Action");
        classeRepository.save(classe);

        mockMvc.perform(delete("/Classe/" + manga.getNserie() + "/Action")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/Classe/" + manga.getNserie() + "/Action")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}