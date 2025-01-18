package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Studio;
import com.example.librairie_online.repository.StudioRepository;

@Service
public class StudioService {
    private StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public Studio create(Studio studio) {
        return this.studioRepository.save(studio);
    }

    public List<Studio> read() {
        return this.studioRepository.findAll();
    }

    public Studio readById(String id) {
        return this.studioRepository.findById(id).orElse(null);
    }

    public Studio update(String id, Studio studio) {
        Studio dbStudio = this.readById(id);
        dbStudio.setNom(studio.getNom());
        dbStudio.setPays(studio.getPays());
        dbStudio.setNomDirecteur(studio.getNomDirecteur());
        dbStudio.setPrenomDirecteur(studio.getPrenomDirecteur());
        return this.studioRepository.save(dbStudio);
    }

    public void delete(String id) {
        this.studioRepository.deleteById(id);
    }

}
