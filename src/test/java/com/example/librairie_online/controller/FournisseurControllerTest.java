package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Fournisseur;
import com.example.librairie_online.service.FournisseurService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FournisseurControllerTest {

    @Mock
    private FournisseurService fournisseurService;

    @InjectMocks
    private FournisseurController fournisseurController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fournisseurController).build();
    }

    @Test
    public void testCreate() throws Exception {
        doNothing().when(fournisseurService).create(any(Fournisseur.class));

        mockMvc.perform(post("/Fournisseur")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Test Fournisseur\"}"))
                .andExpect(status().isCreated());

        verify(fournisseurService, times(1)).create(any(Fournisseur.class));
    }

    @Test
    public void testRead() throws Exception {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Test Fournisseur");
        List<Fournisseur> fournisseurs = Arrays.asList(fournisseur);

        when(fournisseurService.read()).thenReturn(fournisseurs);

        mockMvc.perform(get("/Fournisseur")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'nom':'Test Fournisseur'}]"));

        verify(fournisseurService, times(1)).read();
    }

    @Test
    public void testReadById_Found() throws Exception {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Test Fournisseur");

        when(fournisseurService.readById("1")).thenReturn(fournisseur);

        mockMvc.perform(get("/Fournisseur/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'nom':'Test Fournisseur'}"));

        verify(fournisseurService, times(1)).readById("1");
    }

    @Test
    public void testReadById_NotFound() throws Exception {
        when(fournisseurService.readById("1")).thenReturn(null);

        mockMvc.perform(get("/Fournisseur/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(fournisseurService, times(1)).readById("1");
    }

    @Test
    public void testUpdate_Found() throws Exception {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Test Fournisseur");

        when(fournisseurService.readById("1")).thenReturn(fournisseur);
        doNothing().when(fournisseurService).update(eq("1"), any(Fournisseur.class));

        mockMvc.perform(put("/Fournisseur/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Updated Fournisseur\"}"))
                .andExpect(status().isOk());

        verify(fournisseurService, times(1)).readById("1");
        verify(fournisseurService, times(1)).update(eq("1"), any(Fournisseur.class));
    }

    @Test
    public void testUpdate_NotFound() throws Exception {
        when(fournisseurService.readById("1")).thenReturn(null);

        mockMvc.perform(put("/Fournisseur/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Updated Fournisseur\"}"))
                .andExpect(status().isNotFound());

        verify(fournisseurService, times(1)).readById("1");
        verify(fournisseurService, times(0)).update(eq("1"), any(Fournisseur.class));
    }

    @Test
    public void testDelete_Found() throws Exception {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Test Fournisseur");

        when(fournisseurService.readById("1")).thenReturn(fournisseur);
        doNothing().when(fournisseurService).delete("1");

        mockMvc.perform(delete("/Fournisseur/1"))
                .andExpect(status().isOk());

        verify(fournisseurService, times(1)).readById("1");
        verify(fournisseurService, times(1)).delete("1");
    }

    @Test
    public void testDelete_NotFound() throws Exception {
        when(fournisseurService.readById("1")).thenReturn(null);

        mockMvc.perform(delete("/Fournisseur/1"))
                .andExpect(status().isNotFound());

        verify(fournisseurService, times(1)).readById("1");
        verify(fournisseurService, times(0)).delete("1");
    }
}
