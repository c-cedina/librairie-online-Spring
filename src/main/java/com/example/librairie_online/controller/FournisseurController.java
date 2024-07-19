package com.example.librairie_online.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.http.HttpStatus;
import com.example.librairie_online.entity.Fournisseur;
import com.example.librairie_online.service.FournisseurService;

@RequestMapping(path = "Fournisseur")
@RestController
public class FournisseurController {

    private FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    // Create
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Fournisseur fournisseur) {
        this.fournisseurService.create(fournisseur);
    }

    // Read
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fournisseur> read() {
        return this.fournisseurService.read();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Fournisseur> ReadById(@PathVariable String id) {
        Fournisseur fournisseur = this.fournisseurService.readById(id);
        if (fournisseur != null) {
            return new ResponseEntity<>(fournisseur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable String id, @RequestBody Fournisseur fournisseur) {
        this.fournisseurService.update(id, fournisseur);
    }

    // Delete

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Fournisseur> delete(@PathVariable String id) {
        Fournisseur fournisseur = this.fournisseurService.readById(id);
        if (fournisseur != null) {
            this.fournisseurService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
