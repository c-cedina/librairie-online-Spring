package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.MangaRepository;

@Service
public class MangaService {
    private static final Logger logger = LoggerFactory.getLogger(MangaService.class);

    private MangaRepository mangaRepository;

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public Manga create(Manga manga) {
        logger.info("Création d'un nouveau manga: {}", manga);

        if (mangaRepository.existsByNomAndDateParutionAndTome(
                manga.getNom(), manga.getDateParution(), manga.getTome())) {
            logger.warn("Échec de la création: Le manga '{}' existe déjà.", manga.getNom());
            return null;
        }

        Manga savedManga = this.mangaRepository.save(manga);
        logger.info("Manga créé avec succès: {}", savedManga);
        return savedManga;
    }

    public List<Manga> read() {
        logger.info("Récupération de tous les mangas.");
        List<Manga> mangas = this.mangaRepository.findAll();
        logger.info("Nombre total de mangas récupérés: {}", mangas.size());
        return mangas;
    }

    public Manga readById(int id) {
        logger.info("Recherche d'un manga avec l'ID: {}", id);
        Optional<Manga> optionalManga = this.mangaRepository.findById(id);

        if (optionalManga.isPresent()) {
            logger.info("Manga trouvé: {}", optionalManga.get());
            return optionalManga.get();
        }

        logger.warn("Aucun manga trouvé avec l'ID: {}", id);
        return null;
    }

    public Manga update(int id, Manga manga) {
        logger.info("Mise à jour du manga avec l'ID: {}", id);
        Manga dbManga = readById(id);

        if (dbManga != null) {
            dbManga.setDateParution(manga.getDateParution());
            dbManga.setFournisseur(manga.getFournisseur());
            dbManga.setMangaka(manga.getMangaka());
            dbManga.setNbExemplaire(manga.getNbExemplaire());
            dbManga.setNom(manga.getNom());
            dbManga.setNserie(manga.getNserie());
            dbManga.setTome(manga.getTome());

            Manga updatedManga = this.mangaRepository.save(dbManga);
            logger.info("Manga mis à jour avec succès: {}", updatedManga);
            return updatedManga;
        }

        logger.warn("Échec de la mise à jour: Aucun manga trouvé avec l'ID: {}", id);
        return null;
    }

    public void delete(int id) {
        logger.info("Suppression du manga avec l'ID: {}", id);

        if (mangaRepository.existsById(id)) {
            this.mangaRepository.deleteById(id);
            logger.info("Manga supprimé avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucun manga trouvé avec l'ID: {}", id);
        }
    }
}
