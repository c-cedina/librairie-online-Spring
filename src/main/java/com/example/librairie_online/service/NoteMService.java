package com.example.librairie_online.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.entity.NoteM;
import com.example.librairie_online.entity.NoteM.NoteMId;
import com.example.librairie_online.repository.NoteMRepository;

@Service
public class NoteMService {
    private static final Logger logger = LoggerFactory.getLogger(NoteMService.class);

    private NoteMRepository noteMRepository;
    private MangaService mangaService;
    private ClientService clientService;

    public NoteMService(NoteMRepository noteMRepository, MangaService mangaService, ClientService clientService) {
        this.noteMRepository = noteMRepository;
        this.mangaService = mangaService;
        this.clientService = clientService;
    }

    public NoteM create(NoteM noteM) {
        logger.info("Création d'une nouvelle note pour un manga: {}", noteM);

        Manga manga = mangaService.readById(noteM.getNSerie());
        Client client = clientService.readById(noteM.getNadherent());
        BigDecimal note = noteM.getValeur();

        if (manga != null && client != null) {
            if (note.floatValue() < 0 || note.floatValue() > 5) {
                logger.warn("Échec de la création: La note {} est invalide. Elle doit être comprise entre 0 et 5.",
                        note);
                return null;
            }

            NoteM savedNote = noteMRepository.save(noteM);
            logger.info("Note enregistrée avec succès: {}", savedNote);
            return savedNote;
        }

        logger.warn("Échec de la création: Manga ou Client introuvable.");
        return null;
    }

    public List<NoteM> read() {
        logger.info("Récupération de toutes les notes de mangas.");
        List<NoteM> notes = noteMRepository.findAll();
        logger.info("Nombre total de notes récupérées: {}", notes.size());
        return notes;
    }

    public NoteM readById(NoteMId noteMId) {
        logger.info("Recherche d'une note avec l'ID: {}", noteMId);
        NoteM noteM = noteMRepository.findById(noteMId).orElse(null);

        if (noteM != null) {
            logger.info("Note trouvée: {}", noteM);
        } else {
            logger.warn("Aucune note trouvée avec l'ID: {}", noteMId);
        }

        return noteM;
    }

    public NoteM update(NoteMId id, NoteM noteM) {
        logger.info("Mise à jour de la note avec l'ID: {}", id);
        NoteM noteMDb = readById(id);

        if (noteMDb != null) {
            noteMDb.setValeur(noteM.getValeur());
            noteMDb.setDate(noteM.getDate());

            NoteM updatedNote = noteMRepository.save(noteMDb);
            logger.info("Note mise à jour avec succès: {}", updatedNote);
            return updatedNote;
        }

        logger.warn("Échec de la mise à jour: Aucune note trouvée avec l'ID: {}", id);
        return null;
    }

    public void delete(NoteMId id) {
        logger.info("Suppression de la note avec l'ID: {}", id);

        if (noteMRepository.existsById(id)) {
            noteMRepository.deleteById(id);
            logger.info("Note supprimée avec succès.");
        } else {
            logger.warn("Échec de la suppression: Aucune note trouvée avec l'ID: {}", id);
        }
    }
}
