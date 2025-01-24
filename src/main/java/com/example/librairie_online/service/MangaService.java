package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.MangaRepository;

@Service
public class MangaService {
    private MangaRepository mangaRepository;

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public Manga create(Manga manga) {
        if (mangaRepository.existsByNomAndDateParutionAndTome(
                manga.getNom(), manga.getDateParution(), manga.getTome())) {
            System.err.println("Manga already exists");
            return null;
        }
        return this.mangaRepository.save(manga);
    }

    public List<Manga> read() {
        return this.mangaRepository.findAll();
    }

    public Manga readById(int id) {
        Optional<Manga> optionalManga = this.mangaRepository.findById(id);
        if (optionalManga.isPresent()) {
            return optionalManga.get();
        }
        return null;
    }

    public Manga update(int id, Manga manga) {
        Manga dbManga = readById(id);
        dbManga.setDateParution(manga.getDateParution());
        dbManga.setFournisseur(manga.getFournisseur());
        dbManga.setMangaka(manga.getMangaka());
        dbManga.setNbExemplaire(manga.getNbExemplaire());
        dbManga.setNom(manga.getNom());
        dbManga.setNserie(manga.getNserie());
        dbManga.setTome(manga.getTome());
        return this.mangaRepository.save(dbManga);
    }

    public void delete(int id) {
        this.mangaRepository.deleteById(id);
    }
}
