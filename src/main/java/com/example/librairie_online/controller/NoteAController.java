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

import com.example.librairie_online.entity.NoteA;
import com.example.librairie_online.entity.NoteA.NoteAId;
import com.example.librairie_online.service.NoteAService;

@RequestMapping("/Note/Anime")
@RestController
public class NoteAController {
    private NoteAService noteAService;

    public NoteAController(NoteAService noteAService) {
        this.noteAService = noteAService;
    }

    @PostMapping
    public ResponseEntity<NoteA> create(@RequestBody NoteA noteA) {
        NoteA note = noteAService.create(noteA);
        if (note != null) {
            return new ResponseEntity<>(note, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "{nadherent}/{nserie}")
    public ResponseEntity<NoteA> readById(@PathVariable int nadherent, @PathVariable int nserie) {
        NoteAId noteAId = new NoteAId(nadherent, nserie);
        NoteA note = noteAService.readById(noteAId);
        if (note != null) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<NoteA>> read() {
        return new ResponseEntity<>(noteAService.read(), HttpStatus.OK);
    }

    @PutMapping(path = "{nadherent}/{nserie}")
    public ResponseEntity<NoteA> update(@PathVariable int nadherent, @PathVariable int nserie,
            @RequestBody NoteA noteA) {
        NoteAId noteAId = new NoteAId(nadherent, nserie);
        NoteA note = noteAService.readById(noteAId);
        if (note != null) {
            return new ResponseEntity<>(noteAService.update(noteAId, noteA), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "{nadherent}/{nserie}")
    public ResponseEntity<Void> delete(@PathVariable int nadherent, @PathVariable int nserie) {
        NoteAId noteAId = new NoteAId(nadherent, nserie);
        NoteA note = noteAService.readById(noteAId);
        if (note != null) {
            noteAService.delete(noteAId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
