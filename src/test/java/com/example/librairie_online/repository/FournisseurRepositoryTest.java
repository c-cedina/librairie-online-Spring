package com.example.librairie_online.repository;

import com.example.librairie_online.entity.Fournisseur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class FournisseurRepositoryTest {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    private Fournisseur fournisseur;

    @BeforeEach
    public void setup() {
        fournisseur = new Fournisseur();
        fournisseur.setNom("Test Fournisseur");
        fournisseur.setVille("Test Ville");
        // configure other properties as necessary
    }

    @Test
    public void testCreateFournisseur() {
        Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
        assertNotNull(savedFournisseur);
        assertEquals(fournisseur.getNom(), savedFournisseur.getNom());
    }

    @Test
    public void testReadFournisseurById() {
        fournisseurRepository.save(fournisseur);
        Optional<Fournisseur> foundFournisseur = fournisseurRepository.findById("Test Fournisseur");
        assertTrue(foundFournisseur.isPresent());
        assertEquals(fournisseur.getNom(), foundFournisseur.get().getNom());
    }

    @Test
    public void testUpdateFournisseur() {
        fournisseurRepository.save(fournisseur);
        fournisseur.setVille("Updated Ville");
        Fournisseur updatedFournisseur = fournisseurRepository.save(fournisseur);
        assertEquals("Updated Ville", updatedFournisseur.getVille());
    }

    @Test
    public void testDeleteFournisseur() {
        fournisseurRepository.save(fournisseur);
        fournisseurRepository.deleteById("Test Fournisseur");
        Optional<Fournisseur> foundFournisseur = fournisseurRepository.findById("Test Fournisseur");
        assertFalse(foundFournisseur.isPresent());
    }

    @Test
    public void testFindAllFournisseurs() {
        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setNom("Another Fournisseur");
        fournisseur1.setVille("Another Ville");

        fournisseurRepository.save(fournisseur);
        fournisseurRepository.save(fournisseur1);

        Iterable<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        assertNotNull(fournisseurs);
        assertEquals(2, ((List<Fournisseur>) fournisseurs).size());
    }
}
