package com.example.librairie_online.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.entity.NoteM;
import com.example.librairie_online.entity.NoteM.NoteMId;
import com.example.librairie_online.repository.NoteMRepository;

@Service
public class NoteMService {
    private NoteMRepository noteMRepository;
    private MangaService mangaService;
    private ClientService clientService;

    public NoteMService(NoteMRepository noteMRepository, MangaService mangaService, ClientService clientService) {
        this.noteMRepository = noteMRepository;
        this.mangaService = mangaService;
        this.clientService = clientService;
    }

    public NoteM create(NoteM noteM) {
        Manga manga = mangaService.readById(noteM.getNadherent());
        Client client = clientService.readById(noteM.getNSerie());
        BigDecimal note = noteM.getValeur();
        if (manga != null && client != null) {
            if (note.floatValue() < 0 || note.floatValue() > 5) {
                System.err.println("La note doit être comprise entre 0 et 5");
                return null;
            }
            return noteMRepository.save(noteM);
        }

        return null;
    }

    public List<NoteM> read() {
        return noteMRepository.findAll();
    }

    public NoteM readById(NoteMId noteMId) {
        return noteMRepository.findById(noteMId).orElse(null);
    }

    public NoteM update(NoteMId id, NoteM noteM) {
        NoteM noteMDb = readById(id);
        noteMDb.setValeur(noteM.getValeur());
        noteMDb.setDate(noteM.getDate());
        return noteMRepository.save(noteM);
    }

    public void delete(NoteMId id) {
        noteMRepository.deleteById(id);
    }

}
