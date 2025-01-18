package com.example.librairie_online.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.librairie_online.entity.Manga;

import com.example.librairie_online.service.MangaService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "Manga")
public class MangaController {

    private MangaService mangakaService;

    public MangaController(MangaService mangakaService) {
        this.mangakaService = mangakaService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Manga> read() {
        return this.mangakaService.read();
    }

    @GetMapping("id")
    public ResponseEntity<Manga> readById(@RequestParam int id) {
        Manga manga = this.mangakaService.readByID(id);
        if (manga != null) {
            return new ResponseEntity<>(manga, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
