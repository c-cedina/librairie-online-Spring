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
import com.example.librairie_online.entity.Mangaka;
import com.example.librairie_online.entity.Mangaka.MangakaId;
import com.example.librairie_online.service.MangakaService;

@RestController
@RequestMapping(path = "Mangaka")
public class MangakaController {
    MangakaService mangakaService;

    // Injection dépendance
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

    // Update
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{nom}/{prenom}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable String nom, @PathVariable String prenom, @RequestBody Mangaka mangaka) {
        MangakaId id = new MangakaId(nom, prenom);
        this.mangakaService.update(id, mangaka);
    }

    // Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{nom}/{prenom}")
    public void delete(@PathVariable String nom, @PathVariable String prenom) {
        MangakaId id = new MangakaId(nom, prenom);
        this.mangakaService.delete(id);
    }
}
