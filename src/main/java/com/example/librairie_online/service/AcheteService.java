package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Achete;
import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Manga;
import com.example.librairie_online.repository.AcheteRepository;
import com.example.librairie_online.service.ClientService;
import com.example.librairie_online.service.MangaService;

@Service
public class AcheteService {
    private AcheteRepository acheteRepository;
    private MangaService mangaService;
    private ClientService clientService;

    public AcheteService(AcheteRepository acheteRepository, MangaService mangaService, ClientService clientService) {
        this.acheteRepository = acheteRepository;
        this.mangaService = mangaService;
        this.clientService = clientService;
    }

    public Achete create(Achete achete) {
        Manga manga = mangaService.readById(achete.getManga().getNserie());
        Client client = clientService.readById(achete.getClient().getNAdherent());
        if (client != null && manga != null) {
            return acheteRepository.save(achete);
        }
        return null;
    }

    public Achete readById(int id) {
        return acheteRepository.findById(id).orElse(null);
    }

    public List<Achete> read() {
        return acheteRepository.findAll();
    }

    public Achete update(int id, Achete achete) {
        Achete dbAchete = readById(id);
        dbAchete.setClient(achete.getClient());
        dbAchete.setManga(achete.getManga());
        dbAchete.setDate(achete.getDate());
        dbAchete.setPrix(achete.getPrix());
        return acheteRepository.save(dbAchete);
    }

    public void delete(int id) {
        acheteRepository.deleteById(id);
    }
}
