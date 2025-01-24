package com.example.librairie_online.controller;

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

import com.example.librairie_online.entity.NoteM;
import com.example.librairie_online.entity.NoteM.NoteMId;
import com.example.librairie_online.service.NoteMService;

@RequestMapping("/Note/Manga")
@RestController
public class NoteMController {
    private NoteMService noteMService;

    public NoteMController(NoteMService noteMService) {
        this.noteMService = noteMService;
    }

    @PostMapping
    public ResponseEntity<NoteM> create(@RequestBody NoteM noteM) {
        NoteM note = noteMService.create(noteM);
        if (note != null) {
            return new ResponseEntity<>(note, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{nadherent}/{nSerie}")
    public ResponseEntity<NoteM> readById(@PathVariable int nadherent, @PathVariable int nSerie) {
        NoteMId noteMId = new NoteMId(nadherent, nSerie);
        NoteM note = noteMService.readById(noteMId);
        if (note != null) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> read() {
        return new ResponseEntity<>(noteMService.read(), HttpStatus.OK);
    }

    @PutMapping(path = "/{nadherent}/{nSerie}")
    public ResponseEntity<NoteM> update(@PathVariable int nadherent, @PathVariable int nSerie,
            @RequestBody NoteM noteM) {
        NoteMId noteMId = new NoteMId(nadherent, nSerie);
        NoteM note = noteMService.readById(noteMId);
        if (note != null) {
            return new ResponseEntity<>(noteMService.update(noteMId, noteM), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{nadherent}/{nSerie}")
    public ResponseEntity<?> delete(@PathVariable int nadherent, @PathVariable int nSerie) {
        NoteMId noteMId = new NoteMId(nadherent, nSerie);
        NoteM note = noteMService.readById(noteMId);
        if (note != null) {
            noteMService.delete(noteMId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
