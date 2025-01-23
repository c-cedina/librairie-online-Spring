package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Mangaka;
import com.example.librairie_online.entity.Mangaka.MangakaId;
import com.example.librairie_online.service.MangakaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MangakaControllerTest {

        @Mock
        private MangakaService mangakaService;

        @InjectMocks
        private MangakaController mangakaController;

        private MockMvc mockMvc;

        @BeforeEach
        public void setup() {
                MockitoAnnotations.openMocks(this);
                this.mockMvc = MockMvcBuilders.standaloneSetup(mangakaController).build();
        }

        @Test
        public void testCreate() throws Exception {

                doNothing().when(mangakaService).create(any(Mangaka.class));

                mockMvc.perform(post("/Mangaka")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"id\":{\"nom\":\"Test\",\"prenom\":\"Mangaka\"}, \"sexe\":\"M\", \"nationalite\":\"Japonaise\"}"))
                                .andExpect(status().isCreated());

                verify(mangakaService, times(1)).create(any(Mangaka.class));
        }

        @Test
        public void testRead() throws Exception {
                Mangaka mangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "M", "Japonaise");
                List<Mangaka> mangakas = Arrays.asList(mangaka);

                when(mangakaService.read()).thenReturn(mangakas);

                mockMvc.perform(get("/Mangaka")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content()
                                                .json("[{'id':{'nom':'Test','prenom':'Mangaka'},'sexe':'M','nationalite':'Japonaise'}]"));

                verify(mangakaService, times(1)).read();
        }

        @Test
        public void testReadById_Found() throws Exception {
                Mangaka mangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "M", "Japonaise");

                when(mangakaService.readById(new MangakaId("Test", "Mangaka"))).thenReturn(mangaka);

                mockMvc.perform(get("/Mangaka/Test/Mangaka")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content()
                                                .json("{'id':{'nom':'Test','prenom':'Mangaka'},'sexe':'M','nationalite':'Japonaise'}"));

                verify(mangakaService, times(1)).readById(new MangakaId("Test", "Mangaka"));
        }

        @Test
        public void testReadById_NotFound() throws Exception {
                when(mangakaService.readById(new MangakaId("Test", "Mangaka"))).thenReturn(null);

                mockMvc.perform(get("/Mangaka/Test/Mangaka")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                verify(mangakaService, times(1)).readById(new MangakaId("Test", "Mangaka"));
        }

        @Test
        public void testUpdate_Found() throws Exception {
                Mangaka mangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "M", "Japonaise");

                when(mangakaService.readById(new MangakaId("Test", "Mangaka"))).thenReturn(mangaka);
                doNothing().when(mangakaService).update(eq(new MangakaId("Test", "Mangaka")), any(Mangaka.class));

                mockMvc.perform(put("/Mangaka/Test/Mangaka")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"id\":{\"nom\":\"Test\",\"prenom\":\"Mangaka\"}, \"sexe\":\"M\", \"nationalite\":\"Japonaise\"}"))
                                .andExpect(status().isOk());

                verify(mangakaService, times(1)).readById(new MangakaId("Test", "Mangaka"));
                verify(mangakaService, times(1)).update(eq(new MangakaId("Test", "Mangaka")), any(Mangaka.class));
        }

        @Test
        public void testUpdate_NotFound() throws Exception {
                when(mangakaService.readById(new MangakaId("Test", "Mangaka"))).thenReturn(null);

                mockMvc.perform(put("/Mangaka/Test/Mangaka")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"id\":{\"nom\":\"Test\",\"prenom\":\"Mangaka\"}, \"sexe\":\"M\", \"nationalite\":\"Japonaise\"}"))
                                .andExpect(status().isNotFound());

                verify(mangakaService, times(1)).readById(new MangakaId("Test", "Mangaka"));
                verify(mangakaService, times(0)).update(eq(new MangakaId("Test", "Mangaka")), any(Mangaka.class));
        }

        @Test
        public void testDelete_Found() throws Exception {
                Mangaka mangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "M", "Japonaise");

                when(mangakaService.readById(new MangakaId("Test", "Mangaka"))).thenReturn(mangaka);
                doNothing().when(mangakaService).delete(new MangakaId("Test", "Mangaka"));

                mockMvc.perform(delete("/Mangaka/Test/Mangaka"))
                                .andExpect(status().isOk());

                verify(mangakaService, times(1)).readById(new MangakaId("Test", "Mangaka"));
                verify(mangakaService, times(1)).delete(new MangakaId("Test", "Mangaka"));
        }

        @Test
        public void testDelete_NotFound() throws Exception {
                when(mangakaService.readById(new MangakaId("Test", "Mangaka"))).thenReturn(null);

                mockMvc.perform(delete("/Mangaka/Test/Mangaka"))
                                .andExpect(status().isNotFound());

                verify(mangakaService, times(1)).readById(new MangakaId("Test", "Mangaka"));
                verify(mangakaService, times(0)).delete(new MangakaId("Test", "Mangaka"));
        }
}