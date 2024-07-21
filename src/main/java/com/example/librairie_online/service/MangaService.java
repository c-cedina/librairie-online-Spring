package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.MangaRepository;

@Service
public class MangaService {
    private MangaRepository mangaRepository;

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public List<Manga> read() {
        return this.mangaRepository.findAll();
    }

}
