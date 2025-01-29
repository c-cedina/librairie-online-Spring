package com.example.librairie_online.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Studio;
import com.example.librairie_online.repository.StudioRepository;

@Service
public class StudioService {
    private static final Logger logger = LoggerFactory.getLogger(StudioService.class);

    private StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public Studio create(Studio studio) {
        logger.info("Création d'un nouveau studio: {}", studio);
        Studio savedStudio = this.studioRepository.save(studio);
        logger.info("Studio créé avec succès: {}", savedStudio);
        return savedStudio;
    }

    public List<Studio> read() {
        logger.info("Récupération de tous les studios.");
        List<Studio> studios = this.studioRepository.findAll();
        logger.info("Nombre total de studios récupérés: {}", studios.size());
        return studios;
    }

    public Studio readById(String id) {
        logger.info("Recherche d'un studio avec l'ID: {}", id);
        Studio studio = this.studioRepository.findById(id).orElse(null);

        if (studio != null) {
            logger.info("Studio trouvé: {}", studio);
        } else {
            logger.warn("Aucun studio trouvé avec l'ID: {}", id);
        }

        return studio;
    }

    public Studio update(String id, Studio studio) {
        logger.info("Mise à jour du studio avec l'ID: {}", id);
        Studio dbStudio = this.readById(id);

        if (dbStudio != null) {
            dbStudio.setNom(studio.getNom());
            dbStudio.setPays(studio.getPays());
            dbStudio.setNomDirecteur(studio.getNomDirecteur());
            dbStudio.setPrenomDirecteur(studio.getPrenomDirecteur());

            Studio updatedStudio = this.studioRepository.save(dbStudio);
            logger.info("Studio mis à jour avec succès: {}", updatedStudio);
            return updatedStudio;
        }

        logger.warn("Échec de la mise à jour: Aucun studio trouvé avec l'ID: {}", id);
        return null;
    }

    public void delete(String id) {
        logger.info("Suppression du studio avec l'ID: {}", id);

        if (studioRepository.existsById(id)) {
            this.studioRepository.deleteById(id);
            logger.info("Studio supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun studio trouvé avec l'ID: {}", id);
        }
    }
}
