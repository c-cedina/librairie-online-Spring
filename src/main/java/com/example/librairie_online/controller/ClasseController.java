package com.example.librairie_online.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librairie_online.entity.Classe;
import com.example.librairie_online.entity.Classe.ClasseId;
import com.example.librairie_online.service.ClasseService;

@RequestMapping(path = "/classe")
@RestController
public class ClasseController {
    private ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Classe> create(Classe classe) {
        Classe classeCreate = this.classeService.createClasse(classe);
        return new ResponseEntity<Classe>(classeCreate, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Classe> read() {
        return this.classeService.read();
    }

    @GetMapping(path = "{nserie}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Classe> readById(@PathVariable int nserie, @PathVariable String type) {
        ClasseId id = new ClasseId(nserie, type);
        Classe classeDb = this.classeService.readById(id);
        if (classeDb != null) {
            return new ResponseEntity<Classe>(classeDb, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "{nserie}/{type}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Classe> update(@PathVariable int nserie, @PathVariable String type, Classe classe) {
        ClasseId id = new ClasseId(nserie, type);
        Classe classeDb = this.classeService.readById(id);
        if (classeDb != null) {
            Classe classeDetail = this.classeService.update(id, classe);
            return new ResponseEntity<>(classeDetail, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "{nserie}/{type}")
    public ResponseEntity<Classe> delete(@PathVariable int nserie, @PathVariable String type) {
        ClasseId id = new ClasseId(nserie, type);
        Classe classeDb = this.classeService.readById(id);
        if (classeDb != null) {
            this.classeService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
