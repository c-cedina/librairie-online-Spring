package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Loue;
import com.example.librairie_online.entity.Role;
import com.example.librairie_online.enumeration.TypeRole;
import com.example.librairie_online.repository.AnimeRepository;
import com.example.librairie_online.repository.ClientRepository;
import com.example.librairie_online.repository.LoueRepository;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Import(TestSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoueControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoueRepository loueRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        loueRepository.deleteAll();
        clientRepository.deleteAll();
        animeRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testCreateLoue() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        Client clientDb = clientRepository.save(client);

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        Anime animeDb = animeRepository.save(anime);

        String loueJson = """
                {
                    "client": {"nadherent": %d},
                    "anime": {"nserie": %d},
                    "date": "2023-01-01",
                    "dateRetour": "2023-01-10"
                }
                """.formatted(clientDb.getNAdherent(), animeDb.getNSerie());

        mockMvc.perform(post("/Loue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loueJson))
                .andExpect(status().isCreated());

        // Vérifiez le nombre d'instances de Loue créées
        long count = loueRepository.count();
        assertThat(count).isEqualTo(1); // Changez la valeur attendue en fonction de votre configuration de test
    }

    @Test
    public void testReadLoues() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        clientRepository.save(client);

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        animeRepository.save(anime);

        Loue loue = new Loue();
        loue.setClient(client);
        loue.setAnime(anime);
        loue.setDate(LocalDate.of(2023, 1, 1));
        loue.setDateRetour(LocalDate.of(2023, 1, 10));
        loueRepository.save(loue);

        mockMvc.perform(get("/Loue")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].client.nadherent").value(client.getNAdherent()))
                .andExpect(jsonPath("$[0].anime.nserie").value(anime.getNSerie()))
                .andExpect(jsonPath("$[0].date").value("2023-01-01"))
                .andExpect(jsonPath("$[0].dateRetour").value("2023-01-10"));
    }

    @Test
    public void testUpdateLoue() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        clientRepository.save(client);

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        animeRepository.save(anime);

        Loue loue = new Loue();
        loue.setClient(client);
        loue.setAnime(anime);
        loue.setDate(LocalDate.of(2023, 1, 1));
        loue.setDateRetour(LocalDate.of(2023, 1, 10));
        loue = loueRepository.save(loue);

        String updatedLoueJson = """
                {
                    "client": {"nadherent": %d},
                    "anime": {"nserie": %d},
                    "date": "2023-01-05",
                    "dateRetour": "2023-01-15"
                }
                """.formatted(client.getNAdherent(), anime.getNSerie());

        mockMvc.perform(put("/Loue/" + loue.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedLoueJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2023-01-05"))
                .andExpect(jsonPath("$.dateRetour").value("2023-01-15"));
    }

    @Test
    public void testDeleteLoue() throws Exception {
        Client client = new Client();
        client.setNom("Doe");
        client.setPrenom("John");
        client.setSexe("M");
        client.setAge(30);
        client.setDate_naissance(LocalDate.of(1991, 1, 1));
        client.setDate_adhesion(LocalDate.of(2021, 1, 1));
        Role role = new Role();
        role.setRole(TypeRole.USER);
        role = roleRepository.save(role);
        client.setRole(role);
        clientRepository.save(client);

        Anime anime = new Anime();
        anime.setNom("Naruto");
        anime.setDate(2002);
        animeRepository.save(anime);

        Loue loue = new Loue();
        loue.setClient(client);
        loue.setAnime(anime);
        loue.setDate(LocalDate.of(2023, 1, 1));
        loue.setDateRetour(LocalDate.of(2023, 1, 10));
        loue = loueRepository.save(loue);

        mockMvc.perform(delete("/Loue/" + loue.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/Loue/" + loue.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}