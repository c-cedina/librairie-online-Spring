package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.MangaRepository;

@Service
public class MangaService {
    private MangaRepository mangaRepository;

    public void Create(Manga manga) {
        this.mangaRepository.save(manga);
    }

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public List<Manga> read() {
        return this.mangaRepository.findAll();
    }

    public Manga readByID(int id) {
        Optional<Manga> optionalManga = this.mangaRepository.findById(id);
        if (optionalManga.isPresent()) {
            return optionalManga.get();
        }
        return null;
    }

    public void update(int id, Manga manga) {
        Manga dbManga = readByID(id);
        dbManga.setDate_parution(manga.getDate_parution());
        dbManga.setFournisseur(manga.getFournisseur());
        dbManga.setMangaka(manga.getMangaka());
        dbManga.setNbExemplaire(manga.getNbExemplaire());
        dbManga.setNom(manga.getNom());
        dbManga.setNserie(manga.getNserie());
        dbManga.setTome(manga.getTome());
    }

    public void delete(int id) {
        this.mangaRepository.deleteById(id);
    }
}
