package com.example.librairie_online.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Genre;
import com.example.librairie_online.repository.GenreRepository;

@Service
public class GenreService {
    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre create(Genre genre) {
        logger.info("Création d'un nouveau genre: {}", genre);
        Genre savedGenre = this.genreRepository.save(genre);
        logger.info("Genre créé avec succès: {}", savedGenre);
        return savedGenre;
    }

    public List<Genre> read() {
        logger.info("Récupération de tous les genres.");
        List<Genre> genres = this.genreRepository.findAll();
        logger.info("Nombre total de genres récupérés: {}", genres.size());
        return genres;
    }

    public Genre readById(String id) {
        logger.info("Recherche d'un genre avec l'ID: {}", id);
        Genre genre = this.genreRepository.findById(id).orElse(null);

        if (genre != null) {
            logger.info("Genre trouvé: {}", genre);
        } else {
            logger.warn("Aucun genre trouvé avec l'ID: {}", id);
        }

        return genre;
    }

    public Genre update(String id, Genre genre) {
        logger.info("Mise à jour du genre avec l'ID: {}", id);
        Genre dbGenre = this.readById(id);

        if (dbGenre != null) {
            dbGenre.setType(genre.getType());
            Genre updatedGenre = this.genreRepository.save(dbGenre);
            logger.info("Genre mis à jour avec succès: {}", updatedGenre);
            return updatedGenre;
        }

        logger.warn("Échec de la mise à jour: Aucun genre trouvé avec l'ID: {}", id);
        return null;
    }

    public void delete(String id) {
        logger.info("Suppression du genre avec l'ID: {}", id);

        if (genreRepository.existsById(id)) {
            this.genreRepository.deleteById(id);
            logger.info("Genre supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun genre trouvé avec l'ID: {}", id);
        }
    }
}
