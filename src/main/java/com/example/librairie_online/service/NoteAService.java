package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.NoteA;
import com.example.librairie_online.entity.NoteA.NoteAId;
import com.example.librairie_online.repository.NoteARepository;

@Service
public class NoteAService {
    private NoteARepository noteARepository;
    private AnimeService animeService;
    private ClientService clientService;

    public NoteAService(NoteARepository noteARepository, AnimeService animeService, ClientService clientService) {
        this.noteARepository = noteARepository;
        this.animeService = animeService;
        this.clientService = clientService;

    }

    public NoteA create(NoteA noteA) {
        Anime anime = animeService.readById(noteA.getNSerie());
        Client client = clientService.readById(noteA.getNadherent());
        double note = noteA.getValeur();
        if (anime != null && client != null) {
            if (note < 0 || note > 5) {
                System.err.println("La note doit être comprise entre 0 et 5");
                return null;
            }
            return noteARepository.save(noteA);
        }
        return null;
    }

    public NoteA readById(NoteAId noteAId) {
        return noteARepository.findById(noteAId).orElse(null);
    }

    public List<NoteA> read() {
        return noteARepository.findAll();
    }

    public NoteA update(NoteAId noteAId, NoteA noteA) {
        NoteA dbNoteA = readById(noteAId);
        dbNoteA.setValeur(noteA.getValeur());
        dbNoteA.setDate(noteA.getDate());
        return noteARepository.save(dbNoteA);
    }

    public void delete(NoteAId noteAId) {
        noteARepository.deleteById(noteAId);
    }
}
