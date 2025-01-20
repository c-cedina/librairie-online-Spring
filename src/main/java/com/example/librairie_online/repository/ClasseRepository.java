package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.Classe;
import com.example.librairie_online.entity.Classe.ClasseId;

public interface ClasseRepository extends JpaRepository<Classe, ClasseId> {

}
