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

import com.example.librairie_online.entity.Studio;
import com.example.librairie_online.service.StudioService;

@RestController
@RequestMapping(path = "Studio")
public class StudioController {
    private StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @PostMapping
    public ResponseEntity<Studio> create(Studio studio) {
        Studio studioCreate = this.studioService.create(studio);
        return new ResponseEntity<Studio>(studioCreate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Studio>> read() {
        List<Studio> studios = this.studioService.read();
        return new ResponseEntity<>(studios, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Studio> readById(@PathVariable String id) {
        Studio studioDb = this.studioService.readById(id);
        if (studioDb != null) {
            return new ResponseEntity<Studio>(studioDb, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Studio> update(@PathVariable String id, @RequestBody Studio studio) {
        Studio studioDb = this.studioService.readById(id);
        if (studioDb != null) {
            Studio studioDetail = this.studioService.update(id, studio);
            return new ResponseEntity<>(studioDetail, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Studio> delete(@PathVariable String id) {
        Studio studioDb = this.studioService.readById(id);
        if (studioDb != null) {
            this.studioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
