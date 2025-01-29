package com.example.librairie_online.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.NoteA;
import com.example.librairie_online.entity.NoteA.NoteAId;
import com.example.librairie_online.repository.NoteARepository;

@Service
public class NoteAService {
    private static final Logger logger = LoggerFactory.getLogger(NoteAService.class);

    private NoteARepository noteARepository;
    private AnimeService animeService;
    private ClientService clientService;

    public NoteAService(NoteARepository noteARepository, AnimeService animeService, ClientService clientService) {
        this.noteARepository = noteARepository;
        this.animeService = animeService;
        this.clientService = clientService;
    }

    public NoteA create(NoteA noteA) {
        logger.info("Création d'une nouvelle note: {}", noteA);

        Anime anime = animeService.readById(noteA.getNSerie());
        Client client = clientService.readById(noteA.getNadherent());
        BigDecimal note = noteA.getValeur();

        if (anime != null && client != null) {
            if (note.floatValue() < 0 || note.floatValue() > 5) {
                logger.warn("Échec de la création: La note {} est invalide. Elle doit être comprise entre 0 et 5.",
                        note);
                return null;
            }

            NoteA savedNote = noteARepository.save(noteA);
            logger.info("Note enregistrée avec succès: {}", savedNote);
            return savedNote;
        }

        logger.warn("Échec de la création: Anime ou Client introuvable.");
        return null;
    }

    public NoteA readById(NoteAId noteAId) {
        logger.info("Recherche d'une note avec l'ID: {}", noteAId);
        NoteA noteA = noteARepository.findById(noteAId).orElse(null);

        if (noteA != null) {
            logger.info("Note trouvée: {}", noteA);
        } else {
            logger.warn("Aucune note trouvée avec l'ID: {}", noteAId);
        }

        return noteA;
    }

    public List<NoteA> read() {
        logger.info("Récupération de toutes les notes.");
        List<NoteA> notes = noteARepository.findAll();
        logger.info("Nombre total de notes récupérées: {}", notes.size());
        return notes;
    }

    public NoteA update(NoteAId noteAId, NoteA noteA) {
        logger.info("Mise à jour de la note avec l'ID: {}", noteAId);
        NoteA dbNoteA = readById(noteAId);

        if (dbNoteA != null) {
            dbNoteA.setValeur(noteA.getValeur());
            dbNoteA.setDate(noteA.getDate());

            NoteA updatedNote = noteARepository.save(dbNoteA);
            logger.info("Note mise à jour avec succès: {}", updatedNote);
            return updatedNote;
        }

        logger.warn("Échec de la mise à jour: Aucune note trouvée avec l'ID: {}", noteAId);
        return null;
    }

    public void delete(NoteAId noteAId) {
        logger.info("Suppression de la note avec l'ID: {}", noteAId);

        if (noteARepository.existsById(noteAId)) {
            noteARepository.deleteById(noteAId);
            logger.info("Note supprimée avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucune note trouvée avec l'ID: {}", noteAId);
        }
    }
}
