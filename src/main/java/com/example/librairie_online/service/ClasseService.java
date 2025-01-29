package com.example.librairie_online.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Classe;
import com.example.librairie_online.entity.Genre;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.entity.Classe.ClasseId;
import com.example.librairie_online.repository.ClasseRepository;

@Service
public class ClasseService {
    private static final Logger logger = LoggerFactory.getLogger(ClasseService.class);

    private ClasseRepository classeRepository;
    private MangaService mangaService;
    private GenreService genreService;

    public ClasseService(ClasseRepository classeRepository, MangaService mangaService, GenreService genreService) {
        this.classeRepository = classeRepository;
        this.mangaService = mangaService;
        this.genreService = genreService;
    }

    public Classe create(Classe classe) {
        logger.info("Création d'une nouvelle classe: {}", classe);

        Manga manga = mangaService.readById(classe.getNSerie());
        Genre genre = genreService.readById(classe.getType());

        if (manga != null && genre != null) {
            Classe savedClasse = classeRepository.save(classe);
            logger.info("Classe créée avec succès: {}", savedClasse);
            return savedClasse;
        }

        logger.warn("Échec de la création: Manga ou Genre introuvable.");
        return null;
    }

    public List<Classe> read() {
        logger.info("Récupération de toutes les classes.");
        List<Classe> classes = classeRepository.findAll();
        logger.info("Nombre total de classes récupérées: {}", classes.size());
        return classes;
    }

    public Classe readById(ClasseId id) {
        logger.info("Recherche d'une classe avec l'ID: {}", id);
        Classe classe = classeRepository.findById(id).orElse(null);

        if (classe != null) {
            logger.info("Classe trouvée: {}", classe);
        } else {
            logger.warn("Aucune classe trouvée avec l'ID: {}", id);
        }

        return classe;
    }

    public Classe update(ClasseId id, Classe classe) {
        logger.info("Mise à jour de la classe avec l'ID: {}", id);
        Classe classeDb = readById(id);

        if (classeDb != null) {
            Classe updatedClasse = classeRepository.save(classe);
            logger.info("Classe mise à jour avec succès: {}", updatedClasse);
            return updatedClasse;
        }

        logger.warn("Échec de la mise à jour: Aucune classe trouvée avec l'ID: {}", id);
        return null;
    }

    public void delete(ClasseId id) {
        logger.info("Suppression de la classe avec l'ID: {}", id);

        if (classeRepository.existsById(id)) {
            classeRepository.deleteById(id);
            logger.info("Classe supprimée avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucune classe trouvée avec l'ID: {}", id);
        }
    }
}
