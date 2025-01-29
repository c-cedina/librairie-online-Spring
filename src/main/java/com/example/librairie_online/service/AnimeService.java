package com.example.librairie_online.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.repository.AnimeRepository;

@Service
public class AnimeService {
    private static final Logger logger = LoggerFactory.getLogger(AnimeService.class);

    private AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public Anime createAnime(Anime anime) {
        logger.info("Création d'un nouvel anime: {}", anime);

        if (animeRepository.existsByNomAndDateAndMangaAndStudio(
                anime.getNom(), anime.getDate(), anime.getManga(), anime.getStudio())) {
            logger.warn("Échec de la création: l'anime '{}' existe déjà.", anime.getNom());
            return null;
        }

        Anime savedAnime = animeRepository.save(anime);
        logger.info("Anime créé avec succès: {}", savedAnime);
        return savedAnime;
    }

    public List<Anime> read() {
        logger.info("Récupération de tous les animes.");
        List<Anime> animes = animeRepository.findAll();
        logger.info("Nombre total d'animes récupérés: {}", animes.size());
        return animes;
    }

    public Anime readById(int id) {
        logger.info("Recherche d'un anime avec l'ID: {}", id);
        Anime anime = animeRepository.findById(id).orElse(null);

        if (anime != null) {
            logger.info("Anime trouvé: {}", anime);
        } else {
            logger.warn("Aucun anime trouvé avec l'ID: {}", id);
        }

        return anime;
    }

    public Anime update(int id, Anime anime) {
        logger.info("Mise à jour de l'anime avec l'ID: {}", id);
        Anime animeDb = readById(id);

        if (animeDb != null) {
            animeDb.setNom(anime.getNom());
            animeDb.setDate(anime.getDate());
            animeDb.setManga(anime.getManga());
            animeDb.setStudio(anime.getStudio());

            Anime updatedAnime = animeRepository.save(animeDb);
            logger.info("Anime mis à jour avec succès: {}", updatedAnime);
            return updatedAnime;
        }

        logger.warn("Échec de la mise à jour: Aucun anime trouvé avec l'ID: {}", id);
        return null;
    }

    public void delete(int id) {
        logger.info("Suppression de l'anime avec l'ID: {}", id);

        if (animeRepository.existsById(id)) {
            animeRepository.deleteById(id);
            logger.info("Anime supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun anime trouvé avec l'ID: {}", id);
        }
    }
}
