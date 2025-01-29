package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.entity.NoteM;
import com.example.librairie_online.entity.Role;
import com.example.librairie_online.enumeration.TypeRole;
import com.example.librairie_online.repository.ClientRepository;
import com.example.librairie_online.repository.MangaRepository;
import com.example.librairie_online.repository.NoteMRepository;
import com.example.librairie_online.repository.RoleRepository;
import com.example.librairie_online.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testNoteM")
public class NoteMControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteMRepository noteMRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        noteMRepository.deleteAll();
        clientRepository.deleteAll();
        mangaRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testCreateNoteM() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        client.setEmail("Doe.John@exemple.com");
        client.setPassword("password");
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        clientRepository.save(client);

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        String noteMJson = """
                {
                    "nadherent": %d,
                    "nserie": %d,
                    "valeur": 4.5,
                    "date": "2023-01-01"
                }
                """.formatted(client.getNAdherent(), manga.getNserie());

        mockMvc.perform(post("/Note/Manga")
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteMJson))
                .andExpect(status().isCreated());

        // Vérifiez le nombre d'instances de NoteM créées
        long count = noteMRepository.count();
        assertThat(count).isEqualTo(1); // Changez la valeur attendue en fonction de votre configuration de test
    }

    @Test
    public void testReadNoteMs() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        client.setEmail("Doe.John@exemple.com");
        client.setPassword("password");
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        clientRepository.save(client);

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        NoteM noteM = new NoteM();
        noteM.setNadherent(client.getNAdherent());
        noteM.setNSerie(manga.getNserie());
        BigDecimal noteValeur = new BigDecimal("4.5");
        noteM.setValeur(noteValeur);
        noteM.setDate(LocalDate.of(2023, 1, 1));
        noteMRepository.save(noteM);

        mockMvc.perform(get("/Note/Manga")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nadherent").value(client.getNAdherent()))
                .andExpect(jsonPath("$[0].nserie").value(manga.getNserie()))
                .andExpect(jsonPath("$[0].valeur").value(4.5))
                .andExpect(jsonPath("$[0].date").value("2023-01-01"));
    }

    @Test
    public void testUpdateNoteM() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        client.setEmail("Doe.John@exemple.com");
        client.setPassword("password");
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        clientRepository.save(client);

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        NoteM noteM = new NoteM();
        noteM.setNadherent(client.getNAdherent());
        noteM.setNSerie(manga.getNserie());
        BigDecimal noteValeur = new BigDecimal("4.5");
        noteM.setValeur(noteValeur);
        noteM.setDate(LocalDate.of(2023, 1, 1));
        noteM = noteMRepository.save(noteM);

        String updatedNoteMJson = """
                {
                    "nadherent": %d,
                    "nserie": %d,
                    "valeur": 3.5,
                    "date": "2023-01-05"
                }
                """.formatted(client.getNAdherent(), manga.getNserie());

        mockMvc.perform(put("/Note/Manga/" + client.getNAdherent() + "/" + manga.getNserie())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedNoteMJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valeur").value(3.5))
                .andExpect(jsonPath("$.date").value("2023-01-05"));
    }

    @Test
    public void testDeleteNoteM() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        client.setEmail("Doe.John@exemple.com");
        client.setPassword("password");
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        clientRepository.save(client);

        Manga manga = new Manga();
        manga.setNom("Naruto");
        manga.setDateParution(1999);
        manga.setTome(1);
        manga.setNbExemplaire(10);
        mangaRepository.save(manga);

        NoteM noteM = new NoteM();
        noteM.setNadherent(client.getNAdherent());
        noteM.setNSerie(manga.getNserie());
        BigDecimal noteValeur = new BigDecimal("4.5");
        noteM.setValeur(noteValeur);
        noteM.setDate(LocalDate.of(2023, 1, 1));
        noteM = noteMRepository.save(noteM);

        mockMvc.perform(delete("/Note/Manga/" + client.getNAdherent() + "/" + manga.getNserie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/NoteM/" + client.getNAdherent() + "/" + manga.getNserie())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}