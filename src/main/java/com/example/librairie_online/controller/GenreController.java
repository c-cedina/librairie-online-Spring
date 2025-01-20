package com.example.librairie_online.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.librairie_online.entity.Genre;
import com.example.librairie_online.service.GenreService;

@RequestMapping(path = "/Genre")
@RestController
public class GenreController {
    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<Genre> create(Genre genre) {
        Genre genreCreate = this.genreService.create(genre);
        return new ResponseEntity<Genre>(genreCreate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Genre>> read() {
        List<Genre> genres = this.genreService.read();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Genre> readById(String id) {
        Genre genreDb = this.genreService.readById(id);
        if (genreDb != null) {
            return new ResponseEntity<Genre>(genreDb, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Genre> update(String id, Genre genre) {
        Genre genreDb = this.genreService.readById(id);
        if (genreDb != null) {
            Genre genreDetail = this.genreService.update(id, genre);
            return new ResponseEntity<>(genreDetail, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Genre> delete(String id) {
        Genre genreDb = this.genreService.readById(id);
        if (genreDb != null) {
            this.genreService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
