package com.example.librairie_online.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Achete;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.AcheteRepository;

@Service
public class AcheteService {

    private static final Logger logger = LoggerFactory.getLogger(AcheteService.class);

    private AcheteRepository acheteRepository;
    private MangaService mangaService;
    private ClientService clientService;

    public AcheteService(AcheteRepository acheteRepository, MangaService mangaService, ClientService clientService) {
        this.acheteRepository = acheteRepository;
        this.mangaService = mangaService;
        this.clientService = clientService;
    }

    public Achete create(Achete achete) {
        logger.info("Création d'un achat: {}", achete);

        Manga manga = mangaService.readById(achete.getManga().getNserie());
        Client client = clientService.readById(achete.getClient().getNAdherent());

        if (client != null && manga != null) {
            achete.setClient(client);
            Achete savedAchete = this.acheteRepository.save(achete);
            logger.info("Achat créé avec succès: {}", savedAchete);
            return savedAchete;
        }

        logger.warn("Échec de la création de l'achat: Client ou Manga introuvable.");
        return null;
    }

    public Achete readById(int id) {
        logger.info("Recherche d'un achat avec l'ID: {}", id);
        Achete achete = acheteRepository.findById(id).orElse(null);

        if (achete != null) {
            logger.info("Achat trouvé: {}", achete);
        } else {
            logger.warn("Aucun achat trouvé avec l'ID: {}", id);
        }

        return achete;
    }

    public List<Achete> read() {
        logger.info("Récupération de tous les achats.");
        List<Achete> achats = acheteRepository.findAll();
        logger.info("Nombre total d'achats récupérés: {}", achats.size());
        return achats;
    }

    public Achete update(int id, Achete achete) {
        logger.info("Mise à jour de l'achat avec ID: {}", id);
        Achete dbAchete = readById(id);

        if (dbAchete != null) {
            dbAchete.setClient(achete.getClient());
            dbAchete.setManga(achete.getManga());
            dbAchete.setDate(achete.getDate());
            dbAchete.setPrix(achete.getPrix());
            Achete updatedAchete = acheteRepository.save(dbAchete);
            logger.info("Achat mis à jour avec succès: {}", updatedAchete);
            return updatedAchete;
        }

        logger.warn("Échec de la mise à jour: Aucun achat trouvé avec l'ID: {}", id);
        return null;
    }

    public void delete(int id) {
        logger.info("Suppression de l'achat avec ID: {}", id);

        if (acheteRepository.existsById(id)) {
            acheteRepository.deleteById(id);
            logger.info("Achat supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun achat trouvé avec l'ID: {}", id);
        }
    }
}
