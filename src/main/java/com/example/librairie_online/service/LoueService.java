package com.example.librairie_online.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Loue;
import com.example.librairie_online.repository.LoueRepository;

@Service
public class LoueService {
    private static final Logger logger = LoggerFactory.getLogger(LoueService.class);

    private LoueRepository loueRepository;
    private AnimeService animeService;
    private ClientService clientService;

    public LoueService(LoueRepository loueRepository, AnimeService animeService, ClientService clientService) {
        this.loueRepository = loueRepository;
        this.animeService = animeService;
        this.clientService = clientService;
    }

    public Loue create(Loue loue) {
        logger.info("Création d'une nouvelle location: {}", loue);

        Anime animeDb = this.animeService.readById(loue.getAnime().getNSerie());
        Client clientDb = this.clientService.readById(loue.getClient().getNAdherent());

        if (animeDb != null && clientDb != null) {
            loue.setClient(clientDb);
            Loue savedLoue = this.loueRepository.save(loue);
            logger.info("Location créée avec succès: {}", savedLoue);
            return savedLoue;
        } else {
            logger.warn("Échec de la création: Anime ou Client introuvable.");
            return null;
        }
    }

    public List<Loue> read() {
        logger.info("Récupération de toutes les locations.");
        List<Loue> locations = this.loueRepository.findAll();
        logger.info("Nombre total de locations récupérées: {}", locations.size());
        return locations;
    }

    public Loue readById(int id) {
        logger.info("Recherche d'une location avec l'ID: {}", id);
        Loue loue = this.loueRepository.findById(id).orElse(null);

        if (loue != null) {
            logger.info("Location trouvée: {}", loue);
        } else {
            logger.warn("Aucune location trouvée avec l'ID: {}", id);
        }

        return loue;
    }

    public Loue update(int id, Loue loue) {
        logger.info("Mise à jour de la location avec l'ID: {}", id);
        Loue loueDb = readById(id);

        if (loueDb != null) {
            loueDb.setDate(loue.getDate());
            loueDb.setDateRetour(loue.getDateRetour());

            Loue updatedLoue = this.loueRepository.save(loueDb);
            logger.info("Location mise à jour avec succès: {}", updatedLoue);
            return updatedLoue;
        }

        logger.warn("Échec de la mise à jour: Aucune location trouvée avec l'ID: {}", id);
        return null;
    }

    public void delete(int id) {
        logger.info("Suppression de la location avec l'ID: {}", id);

        if (loueRepository.existsById(id)) {
            this.loueRepository.deleteById(id);
            logger.info("Location supprimée avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucune location trouvée avec l'ID: {}", id);
        }
    }
}
