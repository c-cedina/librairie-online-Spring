package com.example.librairie_online.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.librairie_online.entity.Mangaka;
import com.example.librairie_online.entity.Mangaka.MangakaId;
import com.example.librairie_online.service.MangakaService;

@RestController
@RequestMapping(path = "Mangaka")
public class MangakaController {
    // Injection dépendance
    private MangakaService mangakaService;

    public MangakaController(MangakaService mangakaService) {
        this.mangakaService = mangakaService;
    }

    // Create
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Mangaka mangaka) {
        this.mangakaService.create(mangaka);
    }

    // Read
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Mangaka> read() {
        return this.mangakaService.read();
    }

    @GetMapping(path = "{nom}/{prenom}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mangaka> readById(@PathVariable String nom, @PathVariable String prenom) {
        MangakaId id = new MangakaId(nom, prenom);
        Mangaka mangaka = this.mangakaService.readById(id);
        if (mangaka != null) {
            return new ResponseEntity<>(mangaka, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update

    @PutMapping(path = "{nom}/{prenom}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mangaka> update(@PathVariable String nom, @PathVariable String prenom,
            @RequestBody Mangaka mangakaBody) {
        MangakaId id = new MangakaId(nom, prenom);
        Mangaka mangaka = this.mangakaService.readById(id);
        if (mangaka != null) {
            this.mangakaService.update(id, mangakaBody);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    @DeleteMapping(path = "{nom}/{prenom}")
    public ResponseEntity<Mangaka> delete(@PathVariable String nom, @PathVariable String prenom) {
        MangakaId id = new MangakaId(nom, prenom);
        Mangaka mangaka = this.mangakaService.readById(id);
        if (mangaka != null) {
            this.mangakaService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
