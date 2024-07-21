package com.example.librairie_online.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.MediaType;
import com.example.librairie_online.entity.Manga;

import com.example.librairie_online.service.MangaService;

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
}
