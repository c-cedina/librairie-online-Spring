package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Mangaka;
import com.example.librairie_online.entity.Mangaka.MangakaId;
import com.example.librairie_online.repository.MangakaRepository;

@Service
public class MangakaService {

    private static final Logger logger = LoggerFactory.getLogger(MangakaService.class);

    private MangakaRepository mangakaRepository;

    public MangakaService(MangakaRepository mangakaRepository) {
        this.mangakaRepository = mangakaRepository;
    }

    // Create
    public void create(Mangaka mangaka) {
        logger.info("Création d'un nouveau mangaka: {}", mangaka);
        this.mangakaRepository.save(mangaka);
        logger.info("Mangaka créé avec succès: {}", mangaka);
    }

    // Read
    public List<Mangaka> read() {
        logger.info("Récupération de tous les mangakas.");
        List<Mangaka> mangakas = this.mangakaRepository.findAll();
        logger.info("Nombre total de mangakas récupérés: {}", mangakas.size());
        return mangakas;
    }

    public Mangaka readById(MangakaId id) {
        logger.info("Recherche d'un mangaka avec l'ID: {}", id);
        Optional<Mangaka> optionalMangaka = this.mangakaRepository.findById(id);

        if (optionalMangaka.isPresent()) {
            logger.info("Mangaka trouvé: {}", optionalMangaka.get());
            return optionalMangaka.get();
        }

        logger.warn("Aucun mangaka trouvé avec l'ID: {}", id);
        return null;
    }

    // Update
    public void update(MangakaId id, Mangaka mangaka) {
        logger.info("Mise à jour du mangaka avec l'ID: {}", id);
        Mangaka dbMangaka = readById(id);

        if (dbMangaka != null) {
            dbMangaka.setNationalite(mangaka.getNationalite());
            dbMangaka.setSexe(mangaka.getSexe());
            this.mangakaRepository.save(dbMangaka);
            logger.info("Mangaka mis à jour avec succès: {}", dbMangaka);
        } else {
            logger.warn("Échec de la mise à jour: Aucun mangaka trouvé avec l'ID: {}", id);
        }
    }

    // Delete
    public void delete(MangakaId id) {
        logger.info("Suppression du mangaka avec l'ID: {}", id);

        if (mangakaRepository.existsById(id)) {
            this.mangakaRepository.deleteById(id);
            logger.info("Mangaka supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun mangaka trouvé avec l'ID: {}", id);
        }
    }
}
