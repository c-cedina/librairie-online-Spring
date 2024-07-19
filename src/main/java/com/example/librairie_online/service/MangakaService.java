package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Mangaka;
import com.example.librairie_online.entity.Mangaka.MangakaId;
import com.example.librairie_online.repository.MangakaRepository;

@Service
public class MangakaService {

    private MangakaRepository mangakaRepository;

    public MangakaService(MangakaRepository mangakaRepository) {
        this.mangakaRepository = mangakaRepository;
    }

    // Create
    public void create(Mangaka mangaka) {
        this.mangakaRepository.save(mangaka);

    }

    // Read
    public List<Mangaka> read() {
        return this.mangakaRepository.findAll();
    }

    public Mangaka readById(MangakaId id) {
        Optional<Mangaka> optionalMangaka = this.mangakaRepository.findById(id);
        if (optionalMangaka.isPresent()) {
            return optionalMangaka.get();
        }
        return null;
    }

    // Update
    public void update(MangakaId id, Mangaka mangaka) {
        Mangaka DbMangaka = readById(id);
        DbMangaka.setNationalite(mangaka.getNationalite());
        DbMangaka.setSexe(mangaka.getSexe());
        this.mangakaRepository.save(DbMangaka);
    }

    // Delete
    public void delete(MangakaId id) {
        this.mangakaRepository.deleteById(id);
    }

}
