package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Anime;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Loue;
import com.example.librairie_online.repository.LoueRepository;

@Service
public class LoueService {
    private LoueRepository loueRepository;
    private AnimeService animeService;
    private ClientService clientService;

    public LoueService(LoueRepository loueRepository, AnimeService animeService, ClientService clientService) {
        this.loueRepository = loueRepository;
        this.animeService = animeService;
        this.clientService = clientService;
    }

    public Loue create(Loue loue) {
        Anime animeDb = this.animeService.readById(loue.getAnime().getNSerie());
        Client clientDb = this.clientService.readbyId(loue.getClient().getNAdherent());
        if (animeDb != null && clientDb != null) {
            return this.loueRepository.save(loue);
        } else {
            System.out.println("Anime or Client not found");
            return null;
        }

    }

    public List<Loue> read() {
        return this.loueRepository.findAll();
    }

    public Loue readById(int id) {
        return this.loueRepository.findById(id).orElse(null);
    }

    public Loue update(int id, Loue loue) {
        Loue loueDb = readById(id);
        loueDb.setDateDebut(loue.getDateDebut());
        ;
        loueDb.setDateFin(loue.getDateFin());
        ;
        return this.loueRepository.save(loueDb);
    }

    public void delete(int id) {
        this.loueRepository.deleteById(id);
    }
}
