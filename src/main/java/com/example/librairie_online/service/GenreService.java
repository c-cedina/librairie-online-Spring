package com.example.librairie_online.service;

import java.util.List;

import com.example.librairie_online.entity.Genre;
import com.example.librairie_online.repository.GenreRepository;

public class GenreService {
    private GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre create(Genre genre) {
        return this.genreRepository.save(genre);
    }

    public List<Genre> read() {
        return this.genreRepository.findAll();
    }

    public Genre readById(String id) {
        return this.genreRepository.findById(id).orElse(null);
    }

    public Genre update(String id, Genre genre) {
        Genre dbGenre = this.readById(id);
        dbGenre.setType(genre.getType());
        return this.genreRepository.save(dbGenre);
    }

    public void delete(String id) {
        this.genreRepository.deleteById(id);
    }
}
