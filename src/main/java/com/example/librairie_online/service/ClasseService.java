package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Classe;
import com.example.librairie_online.entity.Genre;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.entity.Classe.ClasseId;
import com.example.librairie_online.repository.ClasseRepository;

@Service
public class ClasseService {
    private ClasseRepository classeRepository;
    private MangaService mangaService;
    private GenreService genreService;

    public ClasseService(ClasseRepository classeRepository, MangaService mangaService, GenreService genreService) {
        this.classeRepository = classeRepository;
        this.mangaService = mangaService;
        this.genreService = genreService;

    }

    public Classe create(Classe classe) {
        Manga manga = mangaService.readById(classe.getNSerie());
        Genre genre = genreService.readById(classe.getType());
        if (manga != null && genre != null) {
            return classeRepository.save(classe);
        }
        return null;
    }

    public List<Classe> read() {
        return classeRepository.findAll();
    }

    public Classe readById(ClasseId id) {
        return classeRepository.findById(id).orElse(null);
    }

    public Classe update(ClasseId id, Classe classe) {
        Classe classeDb = readById(id);
        if (classeDb != null) {
            return classeRepository.save(classeDb);
        }
        return null;
    }

    public void delete(ClasseId id) {
        classeRepository.deleteById(id);
    }

}
