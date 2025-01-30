package com.example.librairie_online.controller;

import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.service.MangaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Manga")
public class MangaController {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping
    public ResponseEntity<List<Manga>> read() {
        List<Manga> mangas = mangaService.read();
        System.out.println(mangas);
        return new ResponseEntity<>(mangas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manga> readById(@PathVariable int id) {
        Manga manga = mangaService.readById(id);
        if (manga != null) {
            return new ResponseEntity<>(manga, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Manga> create(@RequestBody Manga entity) {
        Manga manga = mangaService.create(entity);
        return new ResponseEntity<>(manga, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Manga> update(@PathVariable int id, @RequestBody Manga entityDetails) {
        Manga entity = mangaService.readById(id);
        if (entity != null) {
            mangaService.update(id, entityDetails);
            return new ResponseEntity<>(entityDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Manga entity = mangaService.readById(id);
        if (entity != null) {
            mangaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}