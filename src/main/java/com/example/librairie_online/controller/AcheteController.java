package com.example.librairie_online.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librairie_online.entity.Achete;
import com.example.librairie_online.service.AcheteService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/Achete")
@RestController
public class AcheteController {
    private AcheteService acheteService;

    public AcheteController(AcheteService acheteService) {
        this.acheteService = acheteService;
    }

    @PostMapping
    public ResponseEntity<Achete> create(@RequestBody Achete achete) {
        Achete entity = this.acheteService.create(achete);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Achete>> read() {
        return new ResponseEntity<>(acheteService.read(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Achete> readById(@PathVariable int id) {
        Achete entity = acheteService.readById(id);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Achete> putMethodName(@PathVariable int id, @RequestBody Achete entity) {
        Achete achete = acheteService.readById(id);
        if (achete != null) {
            return new ResponseEntity<>(acheteService.update(id, entity), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Achete> delete(@PathVariable int id) {
        Achete entity = acheteService.readById(id);
        if (entity != null) {
            acheteService.delete(id);
            return new ResponseEntity<>(entity, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
