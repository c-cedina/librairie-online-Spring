package com.example.librairie_online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librairie_online.entity.Classe;
import com.example.librairie_online.entity.Classe.ClasseId;
import com.example.librairie_online.repository.ClasseRepository;

@Service
public class ClasseService {
    private ClasseRepository classeRepository;

    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    public Classe create(Classe classe) {
        return classeRepository.save(classe);
    }

    public List<Classe> read() {
        return classeRepository.findAll();
    }

    public Classe readById(ClasseId id) {
        return classeRepository.findById(id).orElse(null);
    }

    public Classe update(ClasseId id, Classe classe) {
        Classe classeDb = readById(id);
        if (classeDb != null) {
            return classeRepository.save(classeDb);
        }
        return null;
    }

    public void delete(ClasseId id) {
        classeRepository.deleteById(id);
    }

}
