package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.repository.AnimeRepository;

@Service
public class AnimeService {
    private AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public Anime createAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    public List<Anime> read() {
        return animeRepository.findAll();
    }

    public Anime readById(int id) {
        return animeRepository.findById(id).orElse(null);
    }

    public Anime update(int id, Anime anime) {
        Anime animeDb = readById(id);
        if (animeDb != null) {
            animeDb.setNom(anime.getNom());
            animeDb.setDate(anime.getDate());
            animeDb.setManga(anime.getManga());
            animeDb.setStudio(anime.getStudio());
            return animeRepository.save(animeDb);
        }
        return null;
    }

    public void delete(int id) {
        animeRepository.deleteById(id);
    }
}
