package com.example.librairie_online.service;

import com.example.librairie_online.entity.Fournisseur;
import com.example.librairie_online.repository.FournisseurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FournisseurServiceTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @InjectMocks
    private FournisseurService fournisseurService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseurService.create(fournisseur);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    public void testRead() {
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.findAll()).thenReturn(Arrays.asList(fournisseur));

        List<Fournisseur> fournisseurs = fournisseurService.read();
        assertNotNull(fournisseurs);
        assertEquals(1, fournisseurs.size());
        verify(fournisseurRepository, times(1)).findAll();
    }

    @Test
    public void testReadById_Found() {
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.findById(anyString())).thenReturn(Optional.of(fournisseur));

        Fournisseur foundFournisseur = fournisseurService.readById("1");
        assertNotNull(foundFournisseur);
        verify(fournisseurRepository, times(1)).findById("1");
    }

    @Test
    public void testReadById_NotFound() {
        when(fournisseurRepository.findById(anyString())).thenReturn(Optional.empty());

        Fournisseur foundFournisseur = fournisseurService.readById("1");
        assertNull(foundFournisseur);
        verify(fournisseurRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdate() {
        Fournisseur dbFournisseur = new Fournisseur();
        dbFournisseur.setVille("Old City");

        Fournisseur updatedFournisseur = new Fournisseur();
        updatedFournisseur.setVille("New City");

        when(fournisseurRepository.findById(anyString())).thenReturn(Optional.of(dbFournisseur));

        fournisseurService.update("1", updatedFournisseur);

        assertEquals("New City", dbFournisseur.getVille());
        verify(fournisseurRepository, times(1)).findById("1");
        verify(fournisseurRepository, times(1)).save(dbFournisseur);
    }

}
