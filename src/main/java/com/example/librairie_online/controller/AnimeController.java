package com.example.librairie_online.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.service.AnimeService;

@RequestMapping(path = "/Anime")
@RestController
public class AnimeController {
    private AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @PostMapping
    public ResponseEntity<Anime> create(@RequestBody Anime anime) {
        Anime animeCreate = this.animeService.createAnime(anime);
        if (animeCreate == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Anime>(animeCreate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Anime>> read() {
        List<Anime> animes = this.animeService.read();
        return new ResponseEntity<>(animes, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> readById(@PathVariable int id) {
        Anime animeDb = this.animeService.readById(id);
        if (animeDb != null) {
            return new ResponseEntity<Anime>(animeDb, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Anime> update(@PathVariable int id, @RequestBody Anime anime) {
        Anime animeDb = this.animeService.readById(id);
        if (animeDb != null) {
            Anime animeDetail = this.animeService.update(id, anime);
            return new ResponseEntity<>(animeDetail, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Anime> delete(@PathVariable int id) {
        Anime animeDb = this.animeService.readById(id);
        if (animeDb != null) {
            this.animeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
