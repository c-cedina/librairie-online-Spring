package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Fournisseur;
import com.example.librairie_online.repository.FournisseurRepository;

@Service
public class FournisseurService {
    private static final Logger logger = LoggerFactory.getLogger(FournisseurService.class);

    private FournisseurRepository fournisseurRepository;

    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    // Create
    public void create(Fournisseur fournisseur) {
        logger.info("Création d'un nouveau fournisseur: {}", fournisseur);
        this.fournisseurRepository.save(fournisseur);
        logger.info("Fournisseur créé avec succès: {}", fournisseur);
    }

    // Read
    public List<Fournisseur> read() {
        logger.info("Récupération de tous les fournisseurs.");
        List<Fournisseur> fournisseurs = this.fournisseurRepository.findAll();
        logger.info("Nombre total de fournisseurs récupérés: {}", fournisseurs.size());
        return fournisseurs;
    }

    public Fournisseur readById(String id) {
        logger.info("Recherche d'un fournisseur avec l'ID: {}", id);
        Optional<Fournisseur> optionalFournisseur = this.fournisseurRepository.findById(id);

        if (optionalFournisseur.isPresent()) {
            logger.info("Fournisseur trouvé: {}", optionalFournisseur.get());
            return optionalFournisseur.get();
        }

        logger.warn("Aucun fournisseur trouvé avec l'ID: {}", id);
        return null;
    }

    // Update
    public void update(String id, Fournisseur fournisseur) {
        logger.info("Mise à jour du fournisseur avec l'ID: {}", id);
        Fournisseur dbFournisseur = this.readById(id);

        if (dbFournisseur != null) {
            dbFournisseur.setVille(fournisseur.getVille());
            this.fournisseurRepository.save(dbFournisseur);
            logger.info("Fournisseur mis à jour avec succès: {}", dbFournisseur);
        } else {
            logger.warn("Échec de la mise à jour: Aucun fournisseur trouvé avec l'ID: {}", id);
        }
    }

    // Delete
    public void delete(String id) {
        logger.info("Suppression du fournisseur avec l'ID: {}", id);

        if (fournisseurRepository.existsById(id)) {
            this.fournisseurRepository.deleteById(id);
            logger.info("Fournisseur supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun fournisseur trouvé avec l'ID: {}", id);
        }
    }
}
