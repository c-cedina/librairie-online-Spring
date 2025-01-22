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

import com.example.librairie_online.entity.Loue;
import com.example.librairie_online.service.LoueService;

@RestController
@RequestMapping(path = "/Loue")
public class LoueController {
    private LoueService loueService;

    public LoueController(LoueService loueService) {
        this.loueService = loueService;
    }

    @PostMapping
    public ResponseEntity<Loue> create(@RequestBody Loue loue) {
        if (this.loueService.create(loue) != null) {
            return new ResponseEntity<>(this.loueService.create(loue), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Loue>> read() {
        return new ResponseEntity<>(this.loueService.read(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Loue> readById(@PathVariable int id) {
        if (this.loueService.readById(id) != null) {
            return new ResponseEntity<>(this.loueService.readById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Loue> update(@PathVariable int id, @RequestBody Loue loue) {
        if (this.loueService.readById(id) != null) {
            return new ResponseEntity<>(this.loueService.update(id, loue), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (this.loueService.readById(id) != null) {
            this.loueService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
