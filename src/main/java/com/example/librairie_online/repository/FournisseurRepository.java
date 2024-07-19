package com.example.librairie_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librairie_online.entity.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {

}