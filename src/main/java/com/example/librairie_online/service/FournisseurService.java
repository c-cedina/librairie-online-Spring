package com.example.librairie_online.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Fournisseur;
import com.example.librairie_online.repository.FournisseurRepository;

@Service
public class FournisseurService {

    private FournisseurRepository fournisseurRepository;

    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    // Create
    public void create(Fournisseur fournisseur) {
        this.fournisseurRepository.save(fournisseur);
    }

    // Read
    public List<Fournisseur> read() {
        return this.fournisseurRepository.findAll();
    }

    public Fournisseur readById(String id) {
        Optional<Fournisseur> optionalFournisseur = this.fournisseurRepository.findById(id);
        if (optionalFournisseur.isPresent()) {
            return optionalFournisseur.get();
        }
        return null;

    }

    // Update
    public void update(String id, Fournisseur fournisseur) {
        Fournisseur dbFournisseur = this.readById(id);
        dbFournisseur.setVille(fournisseur.getVille());
        this.fournisseurRepository.save(dbFournisseur);
    }

    // Delete
    public void delete(String id) {
        this.fournisseurRepository.deleteById(id);
    }

}
